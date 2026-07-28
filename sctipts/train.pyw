from ultralytics import YOLO

if __name__ == "__main__":
    # 1. Loading of pretrained YOLO26m
    model = YOLO("/root/models/yolo26m.pt")             # The weights will be downloaded automatically if they are not present.

    # 2. Запуск обучения
    results = model.train(
        data="/root/dataset/neu_det_yolo/data.yaml",    # Path to the YAML file with dataset description
        epochs=5000,                                    # Training epochs
        imgsz=200,                                      # Input images size
        patience=100,                                   # Early stopping
        batch=32,                                       # Batch size
        device=0,                                       # GPU index (0, 1, ...) or "cpu" or "auto"
        project="/root/results/train",                  # Directory for results saving
        name="yolo26m_neu_det",                         # Name of the experiment
        exist_ok=True,                                  # Rewrite existing folder
        pretrained=True,                                # Use pretrained weights
        optimizer="auto",                               # Automatic selection of optimizer
        lr0=0.01,                                       # Initial learning rate
        weight_decay=0.0005,                            # Regulatization rate
        momentum=0.937,                                 # SGD Momentum 
        amp=True,                                       # Use automatic mixed precision (AMP) training
        plots=True,                                     # Plots of metrics
        save=True,                                      # Save chectkpoints
        save_period=50,                                 # Save checkpoints every N epochs
        val=True,                                       # Validate after each epoche
    )