# Tugas Praktikum 10

Nama : Muhamad Rafi Ilham  
NIM : 123140173  
Kelas : PAM RB  



## Deskripsi
**Notes App Testing** Pengujian aplikasi Notes App berbasis Kotlin Multiplatform menggunakan beberapa jenis testing untuk memastikan fitur aplikasi berjalan dengan baik dan sesuai.

Testing yang diterapkan:
- Repository Test
- ViewModel Test
- Flow Test
- UI Test Compose

## Implementasi Testing

### Repository Test
Pengujian fungsi CRUD note dan observe data flow.

### ViewModel Test
Pengujian perubahan state dan logic pada ViewModel menggunakan MockK dan Coroutines Test.

### Flow Test
Pengujian Flow menggunakan Turbine untuk memastikan data berhasil di-emits dengan benar.

### UI Test
Pengujian tampilan dan interaksi UI menggunakan Compose UI Test.

Test yang dilakukan:
- Loading state tampil
- Empty state tampil
- Notes list tampil
- Search input berjalan
- Klik item note berhasil

## Library yang Digunakan
- JUnit4
- MockK
- Turbine
- Kotlin Coroutines Test
- Compose UI Test
- Koin Test

## Screenshot Hasil Testing
<img width="1920" height="1031" alt="Screenshot (1006)" src="https://github.com/user-attachments/assets/2378bf99-6550-4631-82c1-08a010829e3b" />

## Video Demonstrasi
https://drive.google.com/file/d/12IfsjgbCNZDRh0kPDN2KRzBdhxPwuDEf/view?usp=sharing
