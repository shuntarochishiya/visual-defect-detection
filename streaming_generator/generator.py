import os
import time
import random
import requests
import numpy as np
from datetime import datetime
from PIL import Image
import io

# Configuration
INGESTION_URL = os.getenv("INGESTION_URL", "http://localhost:8000")
NEU_DET_DIR = "/app/neu_det_images" # Must be /app/neu_det_images, NOT ../neu_det_images

def generate_synthetic_sensor_data(anomaly_probability=0.1):
    """Generates realistic rolling mill sensor data with injected anomalies."""
    is_anomaly = random.random() < anomaly_probability
    
    # Normal ranges
    force = np.random.normal(10, 2)
    speed = np.random.normal(6, 1.5)
    tension = np.random.normal(30, 8)
    temp = np.random.normal(300, 30)
    
    # Inject anomaly if triggered
    if is_anomaly:
        anomaly_type = random.choice(['force_spike', 'temp_spike'])
        if anomaly_type == 'force_spike':
            force += random.uniform(5, 10) # Spike correlates with Inclusions
        elif anomaly_type == 'temp_spike':
            temp += random.uniform(80, 120) # Spike correlates with Shells/Scale
            
    return {
        "timestamp": datetime.now().isoformat(),
        "rolling_force_MN": round(float(force), 2),
        "strip_speed_ms": round(float(speed), 2),
        "tension_kN": round(float(tension), 2),
        "roll_temp_C": round(float(temp), 2)
    }

def stream_images():
    """Reads NEU-DET images and sends them to the ingestion layer."""
    if not os.path.exists(NEU_DET_DIR):
        print(f"Warning: {NEU_DET_DIR} not found. Skipping image stream.")
        return

    images = [f for f in os.listdir(NEU_DET_DIR) if f.endswith(('.jpg', '.png'))]
    if not images:
        return
        
    # Pick a random image to simulate a camera feed
    img_name = random.choice(images)
    img_path = os.path.join(NEU_DET_DIR, img_name)
    
    with open(img_path, "rb") as f:
        files = {'file': (img_name, f, 'image/jpeg')}
        try:
            response = requests.post(f"{INGESTION_URL}/ingest/image", files=files, timeout=10)
            print(f"[Image] Sent {img_name} -> {response.json()}")
        except requests.exceptions.RequestException as e:
            print(f"[Image] Failed to send: {e}")

def stream_sensor_data():
    """Generates and sends time-series data."""
    data = generate_synthetic_sensor_data()
    try:
        response = requests.post(f"{INGESTION_URL}/ingest/timeseries", json=data, timeout=5)
        print(f"[Sensor] Sent data at {data['timestamp']} -> {response.json()}")
    except requests.exceptions.RequestException as e:
        print(f"[Sensor] Failed to send: {e}")

if __name__ == "__main__":
    print(" Starting Streaming Data Generation Service...")
    print(f"Targeting Ingestion Layer at: {INGESTION_URL}")
    
    while True:
        # Simulate factory loop
        stream_images()
        stream_sensor_data()
        
        # Wait 2 seconds before next "frame" / "sensor reading"
        time.sleep(2) 