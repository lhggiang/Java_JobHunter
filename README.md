## JOBHUNTER - Tìm Việc Dễ Dàng Và Nhanh Chóng

## Mục Lục
- [1. GIỚI THIỆU DỰ ÁN](#1-giới-thiệu-dự-án)
- [2. Cấu Hình Cơ Sở Dữ Liệu (MySQL)](#2-cấu-hình-cơ-sở-dữ-liệu-mysql)
- [3. Chạy Ứng Dụng](#3-chạy-ứng-dụng)
- [4. Giới Thiệu Dự Án](#4-giới-thiệu-dự-án)
- [5. Các Chức Năng Chính](#5-các-chức-năng-chính)
- [6. Công Nghệ Sử Dụng](#6-công-nghệ-sử-dụng)
- [7. Cài Đặt & Chạy Dự Án](#7-cài-đặt--chạy-dự-án)
- [8. Tác Giả](#8-tác-giả)
- [9. Demo Chức Năng Ứng Dụng Qua Postman](#9-demo-chức-năng-ứng-dụng-qua-postman)
- [10. Hạn Chế Của Ứng Dụng](#10-hạn-chế-của-ứng-dụng)

## 1. GIỚI THIỆU DỰ ÁN
Trong thế giới ngày càng phát triển hiện nay, việc tìm kiếm một công việc phù hợp với kỹ năng và sở thích của bản thân trở nên khó khăn hơn bao giờ hết. Thị trường lao động hiện nay đang thay đổi nhanh chóng với sự xuất hiện của các công nghệ mới, xu hướng làm việc từ xa và yêu cầu ngày càng cao từ các nhà tuyển dụng. Đối với các ứng viên, việc tìm kiếm công việc không chỉ dừng lại ở việc nộp đơn và chờ đợi. Quá trình này đòi hỏi sự kết nối, cập nhật hồ sơ liên tục, và đôi khi là sự kiên nhẫn trong việc ứng tuyển vào hàng loạt công ty. Còn đối với nhà tuyển dụng, việc lựa chọn ứng viên phù hợp cũng không phải là điều dễ dàng. Việc đăng tin tuyển dụng, sàng lọc hồ sơ và quản lý các ứng viên cần một hệ thống chuyên nghiệp, hiệu quả và dễ dàng sử dụng.

**JobHunter** là một ứng dụng tìm việc làm dễ dàng và nhanh chóng, được xây dựng bằng **Spring** cho phần backend và **MySQL** cho cơ sở dữ liệu. Dự án này nhằm giúp người dùng tìm kiếm việc làm nhanh chóng, hiệu quả, đồng thời giúp nhà tuyển dụng dễ dàng đăng tin tuyển dụng và quản lý hồ sơ của ứng viên.

## 🎯 CÁC CHỨC NĂNG CHÍNH

- **Chức năng đăng ký/đăng nhập/quên mật khẩu/đổi mật khẩu/đăng nhập qua Google**: Hỗ trợ người dùng tạo tài khoản, đăng nhập nhanh chóng, lấy lại mật khẩu và bảo mật tài khoản.
- **Tìm kiếm việc làm**: Cho phép ứng viên tìm việc theo kỹ năng và vị trí việc làm.
- **Ứng tuyển hố sơ**: Ứng viên có thể nộp hồ sơ trực tiếp trên nền tảng mà không cần gửi email hay liên hệ riêng.
- **Quản lý tin tuyển dụng**: Nhà tuyển dụng có thể đăng, chỉnh sửa, và theo dõi tình trạng tin, hồ sơ tuyển dụng của họ.
- **Đề xuất công ty tương tự dựa vào các công ty đã xem**: Hệ thống tự động gợi ý các công ty có liên quan dựa trên lịch sử tìm kiếm của người dùng.
- **Quản lý hồ sơ cá nhân**: Người tìm việc có thể cập nhật và quản lý hồ sơ cá nhân, bao gồm kinh nghiệm làm việc, kỹ năng và thông tin liên hệ.
- **Thống kê và báo cáo**: Cung cấp các báo cáo và thống kê cho người quản trị, nhà tuyển dụng về tình hình ứng tuyển và hiệu quả của các tin tuyển dụng.
- **Đánh giá CV bằng AI dựa vào Job Description**: Hệ thống AI tự động phân tích và đánh giá mức độ phù hợp của CV với mô tả công việc.
- **Gửi thông báo việc làm qua email**: Khi công việc được tạo phù hợp với level, salary, vị trí thì công việc đó sẽ được gửi qua email tự động cho người dùng.

## 🛠 Công Nghệ Sử Dụng

- **Build tool**: Gradle
- **Database**: MySQL
- **DevOps**: Docker, Kafka
 
📌 **Backend**:
- Framework: Spring Boot, Spring Data JPA, Spring Security, Spring REST API
- Authentication: JWT, OAuth2
- Logging & Monitoring: ELK Stack (Elasticsearch, Logstash, Kibana)
- Caching: Redis
- API Documentation & Utilities: Swagger, Postman, Lombok
- Testing: JUnit 5, Mockito

## 🔧 Cài Đặt & Chạy Dự Án

### 1. Cài Đặt Backend 
- Cài đặt JDK 17.
- Clone mã nguồn Backend.
- Mở terminal và chạy lệnh: **./gradlew build -x test**.
- Chạy ứng dụng: **./gradlew bootRun**.

### 2. Cấu Hình Cơ Sở Dữ Liệu (MySQL)
- Cài đặt **MySQL**.
- Tạo cơ sở dữ liệu mới: **CREATE DATABASE jobhunter;**
- Chỉnh sửa cấu hình trong application.properties để kết nối với MySQL.

## 📞 Tác giả:
- **La Hoàng Giang** - [GitHub](https://github.com/lhggiang)

## 🔥 DEMO CHỨC NĂNG ỨNG DỤNG QUA POSTMAN

#### ✅ Skill: có thể thêm, sửa, xóa, lấy danh sách skills (filter + pagination)

Khi lấy danh sách skills phân trang 1 và lấy 3 phần tử, sort theo name giảm dần và filter skills có chứa từ "java" thì kết quả trả ra 2 skills.

![image](https://github.com/user-attachments/assets/b67ff534-852f-4496-ab66-29e118eae8ae)

#### ✅ Company: có thể thêm, sửa, xóa, lấy danh sách companies (filter + pagination), lấy company theo ID, đề xuất công ty tương tự công ty người dùng đã xem

Khi người dùng xem công ty id = 3 (lĩnh vực IT ở HCM) thì hệ thống sẽ đề xuất thêm công ty id = 5 về IT và ở HCM (dùng Redis để in-memory).

![image](https://github.com/user-attachments/assets/9cc8b904-832f-4629-85ce-699902a9e595)
![image](https://github.com/user-attachments/assets/8d8bd98a-0adf-4038-a210-c59bace04f7d)

#### ✅ User: có thể thêm, sửa, xóa, lấy danh sách users (filter + pagination), lấy user theo ID

![image](https://github.com/user-attachments/assets/feb9ba0a-3c33-44db-adf8-25c531e17264)

#### ✅ Subscriber: có thể thêm, sửa subscriber, lấy skills của subscriber

Ứng viên đăng ký kỹ năng, khi có những jobs liên quan đến kỹ năng ứng viên đăng ký sẽ được gửi thông tin qua email.

![image](https://github.com/user-attachments/assets/4df11190-0a40-448d-aba1-b1715f346f0d)

#### ✅ Role: có thể thêm, sửa, xóa, lấy danh sách roles (filter + pagination), lấy role theo ID

Khi lấy danh sách role ta có thể custom để lấy ra danh sách mong đợi, ví dụ lấy ra role có name là HR thì sẽ lấy thông tin của role đó kèm theo permissions. 

![image](https://github.com/user-attachments/assets/559a08d1-f444-4909-842f-99ee83ea6797)

#### ✅ Résume: có thể thêm, sửa, xóa, lấy danh sách résumes (filter + pagination), lấy résume theo ID, phân tích résume dựa vào Job Description (ứng dụng AI)

Lấy lịch sử tất cả résume đã nộp của ứng viên, ví dụ ứng viên admin đã nộp job Dev Python của Company 9.

![image](https://github.com/user-attachments/assets/c2d771ab-ffe7-4cc3-876e-d6f8c418b0df)

#### ✅ Permission: có thể thêm, sửa, xóa, lấy danh sách permissions (filter + pagination)

![image](https://github.com/user-attachments/assets/f0015c2b-064a-49d1-9d72-0a17ddd9361e)

#### ✅ Job: có thể thêm, sửa, xóa, lấy danh sách jobs (filter + pagination), lấy job theo ID

Tạo công việc Java Backend Developer thuộc company id = 4 và cần skill id = 1 và id = 3.

![image](https://github.com/user-attachments/assets/01ddc5dd-8742-4b52-95d3-42f346512ed2)

#### ✅ Auth: dăng ký, đăng nhập (google), quên mật khẩu (gửi qua email), đổi mật khẩu, đăng xuất

![image](https://github.com/user-attachments/assets/04bf8c5a-d678-4b5c-a207-ebf33ff23014)

#### ✅ Thống kê và báo cáo
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

#### ✅ Tích hợp AI để đánh giá sự phù hợp giữa CV và Job Description
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

#### ✅ Quản lý API với Swagger
- Quản lý API với Swagger giúp thiết kế, tài liệu hóa, kiểm thử và tương tác với API trong hệ thống một cách dễ dàng.
  
![image](https://github.com/user-attachments/assets/bb378973-840b-4e9e-84ed-c3e66261d19b)

#### ✅ Quản lý log với ELK 
- Sử dụng ELK (Elasticsearch, Logstash, Kibana) để quản lý log giúp thu thập, xử lý, lưu trữ và trực quan hóa dữ liệu log một cách hiệu quả, hỗ trợ giám sát hệ thống, phân tích lỗi và tối ưu hiệu suất.

![image](https://github.com/user-attachments/assets/33ce27ff-7aa7-476d-b4ee-41d4494519f6)

#### ✅ Kafka 
- Sử dụng Kafka cho tính năng gửi thông báo qua email cho người dùng khi job mới tạo phù hợp với mong muốn của ứng viên.
- Giúp tách rời các service (Job Service (tạo job) và Notification Service (gửi thông báo)), xử lý real-time (thông báo được gửi ngay lập tức khi job được tạo).

Khi công việc vừa được tạo thì công việc đó sẽ được gửi đến ứng viên phù hợp qua email.

![image](https://github.com/user-attachments/assets/7e5e98a1-e3a2-4fce-99bb-0af84fd0b969)

![image](https://github.com/user-attachments/assets/ca08a9e0-38aa-4a89-a855-07e699c472c9)

## 🔧 HẠN CHẾ CỦA ỨNG DỤNG
- Chức năng đánh giá CV sử dụng AI còn hạn chế trong trường hợp các từ đồng nghĩa và CV được viết bằng Tiếng Việt.

