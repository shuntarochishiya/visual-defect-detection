import os
import logging
from fastapi import FastAPI, UploadFile, File, HTTPException, BackgroundTasks
from pydantic import BaseModel
from typing import List, Optional
import httpx
import base64

app = FastAPI(title="Data Ingestion & Messaging Layer (HTTP)")

# Configuration for downstream services (Set via Docker Compose env vars)
VISION_SERVICE_URL = os.getenv("VISION_SERVICE_URL", "http://vision-service:8001/detect")
TIMING_SERVICE_URL = os.getenv("TIMING_SERVICE_URL", "http://timing-service:8002/analyze")

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger("IngestionLayer")

# --- Data Models ---
class SensorData(BaseModel):
    timestamp: str
    rolling_force_MN: float
    strip_speed_ms: float
    tension_kN: float
    roll_temp_C: float

# --- Endpoints ---

@app.get("/")
def read_root():
    return {"status": "Ingestion Layer is running", "version": "1.0.0"}

@app.post("/ingest/image")
async def ingest_image(file: UploadFile = File(...)):
    """Receives an image from the generator and forwards it to the Vision Service."""
    logger.info(f"Received image: {file.filename}")
    
    # Read image bytes
    image_bytes = await file.read()
    
    # Forward to Vision Service
    try:
        async with httpx.AsyncClient() as client:
            # We send the file as multipart/form-data to the vision service
            files = {'file': (file.filename, image_bytes, file.content_type)}
            response = await client.post(VISION_SERVICE_URL, files=files, timeout=10.0)
            
            if response.status_code == 200:
                return {"status": "success", "vision_result": response.json()}
            else:
                logger.warning(f"Vision service returned {response.status_code}")
                return {"status": "forwarded", "vision_result": "Service unavailable or error"}
    except Exception as e:
        logger.error(f"Failed to forward to Vision Service: {e}")
        # Fallback for demo purposes if vision service isn't built yet
        return {"status": "mock_success", "message": "Image received, Vision service not yet ready."}

@app.post("/ingest/timeseries")
async def ingest_timeseries(data: SensorData):
    """Receives sensor data and forwards it to the Time-Series Service."""
    logger.info(f"Received sensor data for timestamp: {data.timestamp}")
    
    # Forward to Time-Series Service
    try:
        async with httpx.AsyncClient() as client:
            response = await client.post(TIMING_SERVICE_URL, json=data.dict(), timeout=5.0)
            
            if response.status_code == 200:
                return {"status": "success", "timing_result": response.json()}
            else:
                return {"status": "forwarded", "timing_result": "Service unavailable or error"}
    except Exception as e:
        logger.error(f"Failed to forward to Timing Service: {e}")
        return {"status": "mock_success", "message": "Sensor data received, Timing service not yet ready."}