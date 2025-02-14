## JOBHUNTER - Tìm Việc Dễ Dàng Và Nhanh Chóng

## 🚀 GIỚI THIỆU DỰ ÁN
Trong thế giới ngày càng phát triển hiện nay, việc tìm kiếm một công việc phù hợp với kỹ năng và sở thích của bản thân trở nên khó khăn hơn bao giờ hết. Thị trường lao động hiện nay đang thay đổi nhanh chóng với sự xuất hiện của các công nghệ mới, xu hướng làm việc từ xa và yêu cầu ngày càng cao từ các nhà tuyển dụng. Đối với các ứng viên, việc tìm kiếm công việc không chỉ dừng lại ở việc nộp đơn và chờ đợi. Quá trình này đòi hỏi sự kết nối, cập nhật hồ sơ liên tục, và đôi khi là sự kiên nhẫn trong việc ứng tuyển vào hàng loạt công ty. Còn đối với nhà tuyển dụng, việc lựa chọn ứng viên phù hợp cũng không phải là điều dễ dàng. Việc đăng tin tuyển dụng, sàng lọc hồ sơ và quản lý các ứng viên cần một hệ thống chuyên nghiệp, hiệu quả và dễ dàng sử dụng.

**JobHunter** là một ứng dụng tìm việc làm dễ dàng và nhanh chóng, được xây dựng bằng **Spring** cho phần backend, **ReactJS** cho frontend và **MySQL** cho cơ sở dữ liệu. Dự án này nhằm giúp người dùng tìm kiếm việc làm nhanh chóng, hiệu quả, đồng thời giúp nhà tuyển dụng dễ dàng đăng tin tuyển dụng và quản lý hồ sơ của ứng viên.

## 🎯 CÁC CHỨC NĂNG CHÍNH

- **Chức năng đăng ký/đăng nhập/quên mật khẩu/đổi mật khẩu/đăng nhập qua Google**: Hỗ trợ người dùng tạo tài khoản, đăng nhập nhanh chóng, lấy lại mật khẩu và bảo mật tài khoản.
- **Tìm kiếm việc làm**: Cho phép ứng viên tìm việc theo kỹ năng và vị trí việc làm.
- **Ứng tuyển hố sơ**: Ứng viên có thể nộp hồ sơ trực tiếp trên nền tảng mà không cần gửi email hay liên hệ riêng.
- **Quản lý tin tuyển dụng**: Nhà tuyển dụng có thể đăng, chỉnh sửa, và theo dõi tình trạng tin, hồ sơ tuyển dụng của họ.
- **Đề xuất công ty dựa vào các công ty đã xem**: Hệ thống tự động gợi ý các công ty có liên quan dựa trên lịch sử tìm kiếm của người dùng.
- **Quản lý hồ sơ cá nhân**: Người tìm việc có thể cập nhật và quản lý hồ sơ cá nhân, bao gồm kinh nghiệm làm việc, kỹ năng và thông tin liên hệ.
- **Thống kê và báo cáo**: Cung cấp các báo cáo và thống kê cho người quản trị, nhà tuyển dụng về tình hình ứng tuyển và hiệu quả của các tin tuyển dụng.
- **Đánh giá CV bằng AI dựa vào Job Description**: Hệ thống AI tự động phân tích và đánh giá mức độ phù hợp của CV với mô tả công việc.

## 🛠 Công Nghệ Sử Dụng

- **Build tool**: Gradle
- **Database**: MySQL
- **DevOps**: Docker
 
📌 **Backend**:
- Spring Boot, Spring Data JPA, Spring Security, Spring REST API 
- Authentication: JWT, OAuth2 
- Logging: ELK (Elaticsearch, Logstash, Kibana)
- Caching data: Redis
- Quản lý API Swagger, Postman, Lombok
- Testing: JUnit 5, Mockito

📌 **Frontend**: 
- HTML, CSS, JS
- ReactJS, Axios, Redux

## 📝 Demo ứng dụng: http://jobhunter.lahoanggiang.io.vn

**ADMIN:**
- Email: admin@gmail.com
- Password: 123456

## 🔧 Cài Đặt & Chạy Dự Án

### 1. Cài Đặt Backend 
- Cài đặt JDK 17.
- Tải mã nguồn backend.
- Mở terminal và chạy lệnh: **./gradlew build -x test**

### 2. Cài Đặt Frontend 
- Cài đặt môi trường **Node.js**.
- Tải mã nguồn frontend và chạy lệnh sau để cài đặt các gói phụ thuộc: **npm install**.
- Build ứng dụng: **npm run build**.
- Chạy ứng dụng ReactJS: **npm run preview**.

### 3. Cấu Hình Cơ Sở Dữ Liệu (MySQL)
- Cài đặt **MySQL**.
- Tạo cơ sở dữ liệu mới: **CREATE DATABASE jobhunter;**
- Chỉnh sửa cấu hình trong application.properties để kết nối với MySQL.

## 📞 Tác giả:
- La Hoàng Giang (Xây dựng Backend) - Đồng tác giả https://github.com/lhggiang
- Trần Đặng Mỹ Phương (Xây dựng Frontend) - Đồng tác giả https://github.com/peonymyx

## 🔥 DEMO CHỨC NĂNG ỨNG DỤNG QUA POSTMAN

### ✅ Thống kê và báo cáo
Sử dụng kiến thức của **Spring Data JPA** và **JPQL (Java Persistence Query Language)** truy vấn dữ liệu.

![image](https://github.com/user-attachments/assets/9df45d06-4a93-4688-991d-2262c769ebbf)

- Thống kê số lượng job, resume, subscriber theo ngày:
![image](https://github.com/user-attachments/assets/3d1f23bb-0dba-4bea-9232-9032096b0cd8)

- Thống kê số lượng job, resume, subscriber theo tháng:
![image](https://github.com/user-attachments/assets/0895817d-95b6-4b3f-8bd7-d25dabd3f7d2)

- Thống kê số lượng job, resume, subscriber theo năm:
![image](https://github.com/user-attachments/assets/31ae3a76-fd09-4801-9a6d-e536a17f6e39)

- Thống kê số lượng subscriber theo skills:
![image](https://github.com/user-attachments/assets/c371e128-7aec-43a5-be80-878d55ee482d)

- Thống kê tổng số lượng company, job, subscriber, các loại resume:
![image](https://github.com/user-attachments/assets/776ab1c3-a9ed-491d-8f25-d3d79c599932)

### ✅ Tích hợp AI để đánh giá sự phù hợp giữa CV và Job Description
Sử dụng ngôn ngữ **Python** để triển khai các kiến thức:
- **FastAPI** - một framework mạnh mẽ và hiệu suất cao để xây dựng API.
- **Sentence Transformers** - tính toán mức độ tương đồng văn bản.
- **nltk (Natural Language Toolkit)** - tiền xử lý văn bản (stopwords, loại bỏ dấu câu).
- **String Manipulation** - chuẩn hóa dữ liệu đầu vào.
- **Pattern Matching** - so sánh kỹ năng CV và JD.
- **Cosine Similarity** - đánh giá mức độ phù hợp giữa CV và JD.

![image](https://github.com/user-attachments/assets/b4b9ae04-7b65-4960-a440-34b3e224f5e5)
![image](https://github.com/user-attachments/assets/26f1747d-d022-459c-a836-d6d744029345)

--> So sánh độ tương đồng giữa CV và Job Description cao nhất là 0.42 và trung bình giữa các lần là 0.12. Đưa ra đánh giá “Low match” (so sánh dựa vào mốc 0.5).

--> Dựa vào danh sách skills của hệ thống (cv_skills) để so sánh độ tương đồng skills trong CV và Job Description thì độ trùng khớp là 100%.

### ✅ Quản lý API với Swagger
- Quản lý API với Swagger giúp thiết kế, tài liệu hóa, kiểm thử và tương tác với API trong hệ thống một cách dễ dàng.
  
![image](https://github.com/user-attachments/assets/bb378973-840b-4e9e-84ed-c3e66261d19b)

### ✅ Quản lý log với ELK 
- Sử dụng ELK (Elasticsearch, Logstash, Kibana) để quản lý log giúp thu thập, xử lý, lưu trữ và trực quan hóa dữ liệu log một cách hiệu quả, hỗ trợ giám sát hệ thống, phân tích lỗi và tối ưu hiệu suất.

![image](https://github.com/user-attachments/assets/33ce27ff-7aa7-476d-b4ee-41d4494519f6)

## 🔧 HẠN CHẾ CỦA ỨNG DỤNG
- Chức năng đánh giá CV sử dụng AI còn hạn chế trong trường hợp các từ đồng nghĩa và CV được viết bằng Tiếng Việt.

