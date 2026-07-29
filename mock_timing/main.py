from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()

class SensorData(BaseModel):
    timestamp: str
    rolling_force_MN: float
    strip_speed_ms: float
    tension_kN: float
    roll_temp_C: float

@app.post("/analyze")
async def analyze(data: SensorData):
    # Pretends to be TimeMixer detecting an anomaly
    is_anomaly = data.rolling_force_MN > 15.0 or data.roll_temp_C > 400.0
    
    if is_anomaly:
        return {
            "status": "success", 
            "model": "TimeMixer", 
            "is_anomaly": True,
            "message": "ALERT: Force spike detected! Correlates with Inclusion defect."
        }
    else:
        return {
            "status": "success", 
            "model": "TimeMixer", 
            "is_anomaly": False,
            "message": "Normal rolling parameters."
        }