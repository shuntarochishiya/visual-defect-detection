from fastapi import FastAPI, UploadFile
app = FastAPI()

@app.post("/detect")
async def detect(file: UploadFile):
    # Pretends to be YOLO/PatchCore finding a defect
    return {
        "status": "success", 
        "model": "YOLOv10", 
        "defect_found": "Inclusion", 
        "confidence": 0.95,
        "message": "Visual defect detected!"
    }