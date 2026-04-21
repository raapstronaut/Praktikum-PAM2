# Tugas Praktikum 7

#### Nama : Muhamad Rafi Ilham
#### NIM  : 123140173
#### Kelas: PAM RB

## Deskripsi
Aplikasi **Notes App** dengan penyimpanan lokal menggunakan **SQLDelight** serta fitur **CRUD**, **Search**, **Settings**, dan konsep **Offline-First**.

## Fitur Utama
- Menyimpan data catatan secara lokal menggunakan **SQLDelight**
- Menampilkan daftar catatan pada halaman utama
- Menambahkan catatan baru (**Create**)
- Menampilkan detail catatan (**Read**)
- Mengedit catatan yang sudah ada (**Update**)
- Menghapus catatan (**Delete**)
- Mencari catatan berdasarkan judul atau isi (**Search**)
- Mengatur **theme** aplikasi (**Light**, **Dark**, **System**)
- Mengatur **sort order** catatan (**Newest First**, **Oldest First**)
- Mendukung konsep **offline-first**, sehingga data tetap dapat diakses tanpa internet
- Menampilkan state **loading**, **empty**, dan **content**

## Database yang Digunakan
- **SQLDelight**
- Schema tabel yang digunakan:
  - `CREATE TABLE Note ( id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, content TEXT NOT NULL, created_at INTEGER NOT NULL, updated_at INTEGER NOT NULL );`

## Cara Menjalankan (Android Studio)
1. Pilih branch **week-7**
2. Clone / download repository:
   - `https://github.com/raapstronaut/Praktikum-PAM.git`
3. Buka folder project tugas praktikum 7 menggunakan Android Studio.
4. Tunggu proses **Gradle Sync** sampai selesai.
5. Jalankan aplikasi dengan menekan tombol **Run**.
6. Pilih emulator/device Android, lalu aplikasi akan terbuka.

## Screenshot Aplikasi

<table>
  <tr>
    <td align="center"><b>1. Loading State</b></td>
    <td align="center"><b>2. Empty State</b></td>
    <td align="center"><b>3. Content State</b></td>
    <td align="center"><b>4. Search Berhasil</b></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/6645198c-10c7-457c-b371-348c85ee289a"width="220" /></td>
    <td><img src="https://github.com/user-attachments/assets/e4b0a70a-aeec-45a6-a7eb-739167499aa8" width="220"/></td>
    <td><img src="https://github.com/user-attachments/assets/f6baf8ec-4cdd-45a3-b506-4c2fa7a6776e" width="220"/></td>
    <td><img src="https://github.com/user-attachments/assets/6fe5718f-1dcc-4128-bb7b-52245ea9e0b0" width="220"/></td>
  </tr>
</table>

<table>
  <tr>
    <td align="center"><b>5. Search Tidak Ditemukan</b></td>
    <td align="center"><b>6. Detail Note</b></td>
    <td align="center"><b>7. Edit Note</b></td>
    <td align="center"><b>8. Settings Screen</b></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/56089a21-76e8-40fa-a1c2-51e881bf1cc7" width="220"/></td>
    <td><img src="https://github.com/user-attachments/assets/2cefe915-152f-487f-be9e-1f633bd42f83" width="220"/></td>
    <td><img src="https://github.com/user-attachments/assets/57919d15-9ba5-4dd8-974c-56d6c4b87722" width="220"/></td>
    <td><img src="https://github.com/user-attachments/assets/d92f4497-6a2f-4b59-bbca-c9aa58aecc83" width="220"/></td>
  </tr>
</table>

<table>
  <tr>
    <td align="center"><b>9. Sort Order - Newest First</b></td>
    <td align="center"><b>9. Sort Order - Oldest First</b></td>
    <td align="center"><b>10. Offline Mode</b></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/43fbb7da-068d-4a45-b0ec-105fc14f1bec" width="220"/></td>
    <td><img src="https://github.com/user-attachments/assets/f3c92dfb-0ec0-45c4-bf88-6deb4faf4302" width="220"/></td>
    <td><img src="https://github.com/user-attachments/assets/f5aa0aad-1a29-4aa5-a37e-bdddb59a348e" width="220"/></td>
  </tr>
</table>

## Link Video
https://drive.google.com/file/d/1n1VgMOC31GOynRKBOfoV3ESQVrSdNY53/view?usp=sharing
