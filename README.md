# Spring Boot + Docker #

##### Roadmap #####

* Overview Docker
* Integrasi Spring Boot dengan Docker
* Tools

## Docker ##

Docker pertama kali dikembangkan oleh **Solomon Hykes** sebagai project internal di dotCloud bersama dengan beberapa koleganya seperti **Andrea Luzzardi** dan **Francois-Xavier Bourlet**. 

> Docker adalah salah satu platform yang dibangun berdasarkan teknologi *container*. Docker merupakan teknologi berbasis *open-source* yang menyediakan platform terbuka untuk developer maupun sysadmin untuk dapat membangun, mengemas dan menjalankan aplikasi dimanapun sebagai sebuah wadah (container) yang ringan.

Docker berfungsi sebagai virtualisasi sebuah sistem operasi atau sebuah server atau sebuah web server atau bahkan sebuah database server, dimana dengan menggunakan virtualisasi ini, diharapkan developer dapat mengembangkan aplikasi sesuai dengan spesifikasi server atau dengan kata lain, jika kita mengembangkan sebuah aplikasi lalu kita jalankan pada komputer kita sendiri maka secara otomatis aplikasi akan berjalan dengan baik, nah bagaimana jika server yang akan menjalankan aplikasi kita memiliki banyak perbedaan dengan komputer kita seperti perbedaan sistem operasi, arsitektur processor dan sebagainya.


#### Virtual Mechine VS Docker ####

![docker-vm](img/docker-vm.jpg)

Dalam membangun program, pengembang biasanya menjalankan virtualisasi pada server sehingga proses pembuatan program dapat berjalan pada berbagai platform maupun konfigurasi hardware. Masalah yang dihadapi dengan virtualisasi adalah perlunya menyiapkan satu sistem operasi secara utuh, termasuk berbagai aplikasi yang dibawa sistem tersebut. Bisa dibayangkan dengan banyaknya virtualisasi yang berjalan di sebuah server akan memberatkan sistem tersebut.

Container kemudian datang dan membawa beberapa perubahan. Dengan container, sebuah program ‘diikat’ beserta library-nya, file konfigurasi, dan seluruh hal yang dibutuhkannya. Perbedaan yang sangat terlihat dibandingkan dengan virtualisasi adalah container memiliki ukuran file yang jauh lebih kecil karena tidak perlu menyiapkan sistem operasi secara penuh. Dalam hal ini, pengembang biasa menyebutnya sebagai ‘lightweight’ platform. Aplikasi yang berjalan menggunakan container pun jauh lebih cepat dan lebih efisien.

#### Instalasi Docker ####

- Installasi Docker pada Mac 
    (https://docs.docker.com/docker-for-mac/)
    
- Installasi Docker pada Windows
    (https://docs.docker.com/docker-for-windows/)

#### Dockerfiles, Images and Containers ####

## Spring Boot To Docker ##

#### Aplikasi Spring Boot ####

#### Docker Compose ####

Learn how to use Docker Compose to run multi-container applications easily


## Tools untuk Mengelola Docker ##

- Kitematic (https://kitematic.com)
- Portainer (https://portainer.io)