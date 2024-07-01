# Sử dụng base image có sẵn của Python
FROM python:3.12.2

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Sao chép tất cả các file từ thư mục hiện tại của bạn vào /app trong container
COPY . /app

# Cài đặt các dependency từ requirements.txt
RUN pip install --no-cache-dir -r /app/requirements.txt

# Chạy lệnh khi container được khởi động
CMD ["python", "/app/your_script.py"]
