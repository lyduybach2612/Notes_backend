# Notes Backend

## Giới thiệu
- Notes Backend là hệ thống Backend của ứng dụng Notes.
- Ứng dụng được xây dựng dựa trên RESTfulAPI.
- Ứng dụng được tích hợp Security với JWT.
- Ứng dụng sử dụng Method Authorization.
- Ứng dụng bao gồm các chức năng đăng nhập, đăng kí, sửa thông tin cho người dùng cùng với các chức năng CRUD liên quan tới ghi chú.
- Ứng dụng sử dụng Swagger để kiểm thử API và tạo API documentation.

## Chức năng
- Hệ thống cung cấp các chức năng đăng nhập, đăng kí cho phép người dùng sử dụng ứng dụng. Sau khi đăng nhập sẽ trả về một JWT.
- Hệ thống cho phép người dùng đổi thông tin cá nhân.
- Các chức năng liên quan tới CRUD(Create, Read, Update, Delete) ghi chú (có tích hợp phân trang).

## Database
- MySQL

## Công nghệ sử dụng
- Spring Boot, Spring Security x JWT, RESTfulAPI, Swagger, Mapstruct

