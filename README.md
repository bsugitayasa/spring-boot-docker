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

Dalam membangun program, pengembang biasanya menjalankan virtualisasi pada server sehingga proses pembuatan program dapat berjalan pada berbagai platform maupun konfigurasi hardware. Masalah yang dihadapi dengan virtualisasi adalah perlunya menyiapkan satu sistem operasi secara utuh, termasuk berbagai aplikasi yang dibawa sistem tersebut. Bisa dibayangkan dengan banyaknya virtualisasi yang berjalan di sebuah server akan memberatkan sistem tersebut.


![docker-vm](img/docker-vm.jpg)

Container kemudian datang dan membawa beberapa perubahan. Dengan container, sebuah program ‘diikat’ beserta library-nya, file konfigurasi, dan seluruh hal yang dibutuhkannya. Perbedaan yang sangat terlihat dibandingkan dengan virtualisasi adalah container memiliki ukuran file yang jauh lebih kecil karena tidak perlu menyiapkan sistem operasi secara penuh. Dalam hal ini, pengembang biasa menyebutnya sebagai *lightweight* platform. Aplikasi yang berjalan menggunakan container pun jauh lebih cepat dan lebih efisien.

Berbeda dengan virtualisasi yang mana aplikasi berjalan di atas hypervisor dan guest OS, docker dapat menjalankan aplikasi langsung tanpa kedua hal tadi. Docker juga dilengkapi dengan fitur sandbox yang menjamin pengerjaan pengembang dan sysadmin tidak terganggu. Sandbox pada istilah keamanan komputer adalah mekanisme pemisahan aplikasi atau program tanpa mengganggu host (isolasi).


#### Arsitektur Docker ####

Docker terdiri dari beberapa element diantaranya Docker Images dan Docker Container.

![docker-view] (img/docker-view.jpg)


##### Docker Image #####

Docker image adalah sebuah template yang bersifat read only. Template ini sebenarnya adalah sebuah OS atau OS yang telah diinstall berbagai aplikasi. Docker image berfungsi untuk membuat docker container, dengan hanya 1 docker image kita dapat membuat banyak docker container.


##### Docker Container #####

Docker container bisa dikatakan sebagai sebuah folder, dimana docker container ini dibuat dengan menggunakan docker image. Setiap docker container disimpan maka akan terbentuk layer baru tepat diatas docker image atau base image diatasnya. Satu atau lebih docker container dapat dibuat dari 1 docker image dengan menggunakan perintah `docker run`. Docker container ini nantinya dapat dibuild sehingga akan menghasilkan sebuah docker image, dan docker image yang dihasilkan dari docker container ini dapat kita gunakan kembali untuk membuat docker container yang baru.


#### Instalasi Docker ####

Proses installasi docker dapat dengan mudah dilakukan baik pada platform Windows, Linux maupun Mac-os. Tahapan installasi docker dapat diliat pada tautan berikut:

- Installasi Docker pada Mac 
    (https://docs.docker.com/docker-for-mac/)
    
- Installasi Docker pada Windows
    (https://docs.docker.com/docker-for-windows/)


## Spring Boot To Docker ##

Berikut adalah bagaimana membuat sebuah project sederhana berbasis spring boot dan build sebagai sebuah image ke dalam docker.

#### Aplikasi Spring Boot ####

Pertama kita akan membuat aplikasi spring boot melalui spring initializr:

1. Browse ke [https://start.spring.io/] (https://start.spring.io/)

2. Lengkapi Project Metadata

    Group
    
    ```java
    com.sheringsession.balicamp.springdocker    
    ```

    Artifact
    
    ```
    spring-demo1
    ```
   
    Dependencies
    
    ```java
    Web
    ```
   
3. Generate Project

4. Download dan import pada IDE masing-masing

5. Tambahkan pada class com.sheringsession.balicamp.springdocker.springdemo1.SpringDemo1Application seperti potongan code berikut:

    ```java
    @SpringBootApplication
    @RestController
    public class SpringDemo1Application {

        @RequestMapping("/spring/demo1/")
        public String hello(){
            return "Hello Docker World from Spring-Demo-1";
        }

        public static void main(String[] args) {
            SpringApplication.run(SpringDemo1Application.class, args);
        }
    }
    ```
    
6. Build dan Run via Maven 

    ```java
    mvn spring-boot:run
    ```
    
7. Coba akses via browser local ke halaman : http://localhost:8080/spring/demo1/

Setelah aplikasi sederhana berjalan dan memberikan response di local, selanjutnya bagaimana melakukan build aplikasi spring boot menjadi image pada docker.


#### Dockerfiles ####

![dockerfile] (img/dockerfile.jpg)

Docker telah menyediakan dockerfile format yang digunakan untuk spesifik layer image pada docker. Dengan menggunakan dockerfile kita dapat mem-build sebuah aplikasi pada docker termasuk komponen os maupun konfigurasi yang diinginkan. Langkah selanjutnya buat sebuah Dockerfile pada aplikasi spring boot.

```dockerfile
FROM openjdk:8-jdk-alpine
ADD target/spring-demo1.jar demo1.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /demo1.jar
```

Format dockerfile diatas berfungsi untuk menambah spring-demo1.jar kedalam docker sebagai demo1.jar dan selanjutya mengeksekusi *ENTRYPOINT* sesuai perintah pada dockerfile. Sedangkan untuk mengurangi waktu startup aplikasi embeded tomcat yang dibawa oleh spring boot maka kita dapat menambahkan sistem properties yang menunjuk ke /dev/urandom pada container docker. Openjdk:8-jdk-alpine akan otomatis di `pull` dari dockerhub jika memang belum tersedia pada image repository lokal.

Untuk melakukan build docker image menggunakan maven, maka perlu menambahkan plugin pada bagian pom.xml hasil pengembangan dari [Spotify] (https://github.com/spotify/dockerfile-maven) sebagai berikut:

```java
<properties>
   <docker.image.prefix>springdocker</docker.image.prefix>
</properties>
<build>
    <finalName>${project.artifactId}</finalName>
    <plugins>
        <plugin>
            <groupId>com.spotify</groupId>
            <artifactId>dockerfile-maven-plugin</artifactId>
            <version>1.3.4</version>
            <configuration>
                <repository>${docker.image.prefix}/${project.artifactId}</repository>
            </configuration>
        </plugin>
    </plugins>
</build>
```

Konfigurasi diatas akan menghasilkan :

* Repository image pada docker dengan nama springdocker/spring-demo1
* Secara default image pada docker akan diversioning dengan *latest* jika tidak diberikan versi, biasanya dapat diset menggunakan artifact id

Kemudian untuk menjalankan via maven dapat menggunakan perintah berikut

```java
$ mvn clean install dockerfile:build
```

> Perlu dipastikan docker pada komputer telah menyala sebelum melakukan build project (dapat menggunakan perintah `docker ps`)


Jika proses build sukses maka untuk melakukan pengecekan apakah image telah terbentuk pada docker, dapat menggunakan perintah berikut:

```docker
$ docker images
REPOSITORY                                      TAG                 IMAGE ID            CREATED                     SIZE
springdocker/spring-demo1                       latest              ed59fd26342f        Less than a second ago      116MB
openjdk                                         8-jdk-alpine        a2a00e606b82        Less than a second ago      101MB
```

Pada list repository image diatas, dapat dilihat terdapat 2 image hasil build aplikasi spring boot berdasarkan dockerfile diatas. Selanjutnya, bagaimana cara menjalankan image spring-demo1 menjadi container yang siap diakses, berikut adalah perintahnya:

```docker
$ docker run -p 8080:8080 -t springdocker/spring-demo1
....
2017-11-13 07:19:32.469  INFO 1 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2017-11-13 07:19:32.861  INFO 1 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
2017-11-13 07:19:32.899  INFO 1 --- [           main] c.s.b.s.s.SpringDemo1Application         : Started SpringDemo1Application in 15.307 seconds (JVM running for 17.809)
```

Setelah container dinyatakan telah start, maka akses melalui web browser : [http://localhost:8080/spring/demo1/] (http://localhost:8080/spring/demo1/)

Untuk mengetahui container apa saja yang sedang berjalan, maka dapat menggunakan perintah 

```docker
$ docker ps
CONTAINER ID        IMAGE                       COMMAND                  CREATED             STATUS              PORTS                    NAMES
ca59de77206d        springdocker/spring-demo1   "/bin/sh -c 'exec ..."   2 minutes ago       Up 2 minutes        0.0.0.0:8080->8080/tcp   festive_minsky
```

> **NAMES** (festive_minsky) pada container adalah alias yang jika tidak didefine saat melakukan perintah `docker run`, maka akan otomatis diberikan oleh docker secara random

Bagaimana melakukan stop proses pada container? Caranya sangat mudah, hanya dengan menggunakan perintah 

```docker
$ docker stop festive_minsky
festive_minsky
```

Apa yang terjadi jika kita melakukan perubahan pada source code lokal dan harus melakukan build image dengan kondisi ter up to date? Cara yang paling sederhana, dengan melakukan maven build, melakukan stop dan delete container bersangkutan dan run kembali. Namun jika memiliki lebih dari satu aplikasi (misal : microservices) maka akan memakan waktu yang cukup banyak dalam prosesnya.
Docker telah menyediakan fitur yang dapat melakukan build sekaligus run secara bersamaan tanpa harus melakukan penghapusan pada container yang telah terbentuk.

#### Docker Compose ####

Setelah melakukan build dengan menggunakan Dockerfile untuk membentuk sebuah image pada docker, selanjutnya kita akan menggunakan *docker-compose*.

> Docker compose berfungsi untuk menjalankan multi-container docker secara bersamaan secara mudah.

Docker compose ini sangat berguna ketika aplikasi kita terpisah - pisah pada komputer yang berbeda, contohnya adalah aplikasi yang dibuat berada pada 1 container sedangkan database yang akan digunakan oleh aplikasi tersebut berada pada container yang lain. Ketika menggunakan docker compose maka kita dapat menjalankan kedua container tersebut secara bersamaan dan bahkan kita dapat melakukan link ke container yang kita inginkan.

Docker compose biasanya telah include terinstall ketika kita melakukan installasi docker. Cara mudah untuk mengetahui docker compose pada komputer dapat melakukan pengecekan pada command line / terminal dengan perintah:

```terminal
$ docker-compose -v
docker-compose version 1.16.1, build 6d1ac21
```

Untuk lebih memahami penggunakan docker compose, maka langkah selanjutnya mencoba membuat sebuah aplikasi spring boot (seperti langkah sebelumnya), misalnya diberi nama spring-demo2.

Berikutnya pada root directory dibuat sebuah file dengan nama docker-compose.yml yang isinya sebagai berikut:

```yml
version: "3.3" 
services:
  spring-demo1:
    build: spring-demo1/
    image: springdocker/spring-demo1:latest
    container_name: spring-demo1
    ports:
      - 8080:8080
    hostname: spring.demo.1
    
  spring-demo2:
    build: spring-demo2/
    image: springdocker/spring-demo2:latest
    container_name: spring-demo2
    ports:
      - 8081:8080
    hostname: spring.demo.2
    restart: on-failure
    depends_on:
      - spring-demo1
```

File docker-compose.yml diatas merupakan file configurasi docker compose untuk melakukan beberapa perintah internal docker. 
* version : merupakan versi dari docker-compose file format yang mengacu pada versi docker engine sesuai dokumen [Compose and Docker compatibility matrix] (https://docs.docker.com/compose/compose-file/)
* services : adalah header list untuk mendefinisikan service apa saja yang akan di build dan run pada docker yang terdiri dari:
    * spring-demo1 : inisial nama service 
    * build : folder direktory path yang akan dilakukan build (termasuk didalamnya terdapat Dockerfile)
    * image : adalah repository name dari image hasil build docker
    * container_name : inisial nama container (yang jika dikosongkan/dihilangkan akan default diberi nama inisial random oleh docker)
    * ports : adalah list port yang digunakan untuk memetakan antara port internal container docker dengan port yang dapat diakses dari luar container
    * hostname : hostname dari container, lebih berfungsi jika memerlukan komunikasi antar container dalam 1 docker engine (default ip container adalah hostnamenya)
    * restart : ketentuan melakukan restart pada container jika terjadi kegagalan saat melakukan run container, biasanya berkaitan dengan docker engine yang direstart atau adanya link dengan container lain
    * depends_on : menandakan start order service container. Dalam hal ini, service spring-demo2 akan di jalankan jika service spring-demo1 telah dijalankan namun tidak menunggu hingga proses start telah selsai


Untuk menjalankan docker-compose pada root folder cukup dengan menjalankan perintah berikut pada command-line atau terminal

```docker
$ docker-compose up
Starting spring-demo1 ... 
Starting spring-demo1 ... done
Starting spring-demo2 ... 
Starting spring-demo2 ... done
Attaching to spring-demo1, spring-demo2
....

```

Perintah `docker-compose up` diatas dapat diliat sekaligus menjalankan 2 service sesuai konfigurasi pada docker-compose.yml. Untuk memantau docker container yang telah aktif dapat menggunakan perintah berikut pada comman-line/terminal terpisah

```docker
$ docker ps
CONTAINER ID        IMAGE                              COMMAND                  CREATED             STATUS              PORTS                    NAMES
304f1e4ae8e2        springdocker/spring-demo2:latest   "/bin/sh -c 'exec ..."   5 minutes ago       Up 3 minutes        0.0.0.0:8081->8080/tcp   spring-demo2
f371eeb488ac        springdocker/spring-demo1:latest   "/bin/sh -c 'exec ..."   6 minutes ago       Up 3 minutes        0.0.0.0:8080->8080/tcp   spring-demo1
```

Akses kedua service tersebut dapat melalui 
- [http://localhost:8080/spring/demo1/] (http://localhost:8080/spring/demo1/)
- [http://localhost:8081/spring/demo2/] (http://localhost:8081/spring/demo2/)

Untuk terminate proses nya, tinggal menggunakan `ctrl+c` maka akan mendapatkan response berikut

```docker
Gracefully stopping... (press Ctrl+C again to force)
Stopping spring-demo2 ... done
Stopping spring-demo1 ... done
```

## Tools untuk Mengelola Docker ##

Selain menggunakan beberapa command pada comman-line atau terminal untuk melakukan `start`, `stop`, `delete` dan perintah lainnya, beberapa perusahaan telah menyediakan tools untuk mengelola docker engine. Tools tersebut dapat diunduh secara gratis dan dapat langsung di `pull` sebagai docker image melalui [dockerhub] (https://hub.docker.com/explore/). 

Berikut adalah tools untuk mengelola docker engine:

- Kitematic (https://kitematic.com)
- Portainer (https://portainer.io)


## Referensi ##

* [docker.com] (https://www.docker.com)
* [Docker Compose] (https://docs.docker.com/compose/)
* [Learn Docker in 12 Minutes] (https://youtu.be/YFl2mCHdv24)
* [Spring Boot with Docker] (https://spring.io/guides/gs/spring-boot-docker/)
* [Portainer.io] (https://portainer.io/)
* [Kitematic] (https://kitematic.com/)
* [Spotify Dockerfile Maven] (https://github.com/spotify/dockerfile-maven)