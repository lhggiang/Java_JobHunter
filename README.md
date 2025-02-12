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
- Quản lý API Swagger
- Postman, Lombok, SonarQube
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
  
