# Tugas Praktikum 6

#### Nama : Muhamad Rafi Ilham
#### NIM  : 123140173
#### Kelas: PAM RB

## Deskripsi
Aplikasi **News Reader** dengan integrasi **NewsAPI** menggunakan **Ktor Client**.

## Fitur Utama
- Mengambil data berita dari public API (**NewsAPI**)
- Menampilkan daftar artikel berita
- Menampilkan **title**, **description**, dan **image** pada setiap artikel
- Menampilkan **detail screen** saat artikel diklik
- Mendukung **pull to refresh**
- Menampilkan state **loading**, **success**, dan **error**
- Menggunakan **Repository Pattern** untuk pemanggilan API

## API yang Digunakan
- **NewsAPI**
- Endpoint yang digunakan:
  - `https://newsapi.org/v2/top-headlines?country=us&category=technology&apiKey=138e07f08dfa4a9081c54fedffb880cb`

## Cara Menjalankan (Android Studio)
1. Pilih branch **week-6**
2. Clone / download repository:
   - `https://github.com/raapstronaut/Praktikum-PAM.git`
3. Buka folder project tugas praktikum 6 menggunakan Android Studio.
4. Tunggu proses **Gradle Sync** sampai selesai.
5. Pastikan API key NewsAPI sudah dimasukkan pada file:
   - `NewsRepository.kt`
5. Jalankan aplikasi dengan menekan tombol **Run**.
6. Pilih emulator/device Android, lalu aplikasi akan terbuka dan menampilkan daftar berita.

## Screenshot Aplikasi

<table>
  <tr>
    <td align="center"><b>Loading State</b></td>
    <td align="center"><b>Success State</b></td>
    <td align="center"><b>Error State</b></td>
    <td align="center"><b>Detail Screen</b></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/363b2950-7271-4708-a073-dc9a1d7d9a32" width="220" /></td>
    <td><img src="https://github.com/user-attachments/assets/68f457f6-a37b-4439-b7cd-7c6646befe0d" width="220"/></td>
    <td><img src="https://github.com/user-attachments/assets/7b3c07d4-e5fc-4e3f-aadc-0787eabc7335" width="220"/></td>
    <td><img src="https://github.com/user-attachments/assets/2b261208-1a56-47fd-ba88-0cdbcb9b2ce4" width="220"/></td>
  </tr>
</table>

## Link Video
[https://drive.google.com/file/d/1afCmNpPLJeDK0HdFOQlTGO64R9j6LoEQ/view?usp=drive_link](https://drive.google.com/file/d/1IVw0x_vXS13syrnVyyBDrW3-At3p2kM2/view?usp=sharing)
