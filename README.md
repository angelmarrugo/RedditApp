# Reddit App by Angel Marrugo Plata :trophy:
[APK](https://drive.google.com/file/d/1T7ZbugsMuLm1UbjP9_HkRYC5gbSKEFaj/view?usp=sharing)

Aplicativo básico construido para Android que incluye las mejores prácticas de arquitectura para desarrollar apps sólidas y de calidad de producción. 

## Arquitectura :rocket:
El modelo de arquitectura que se siguió para la construcción de esta app es el recomendado por Google en su [Guía de arquitectura de apps](https://developer.android.com/jetpack/guide)
Consta de las siguientes capas:
<ul>
<li>Capa ui: Encargada sola de las interacciones con la UI y el sistema operativo; MainActivity, TopPostFragment, PostAdapter</li>
<li>Capa viewmodel: Encargada de propocionarle los datos a la capa ui; TopPostViewModel</li>
<li>Capa Data: Si bien la capa donde se encuentra el viewmodel es la encargada de proporcionar los datos a la ui, es la capa data quien obtiene estos datos, la obtencion de estos datos puede ser por una servicio Api Rest o con la persistencia de datos locales. En esta aplicacion se hizo uso de paging3 el cual sigue el mismo patron de arquitectura que hemos visto, por lo que las clases son: RedditRemoteMediator y RedditRepository</li>
<li>Capa db: que es donde esta contenida la logica para la persistencia de datos; RedditDatabase</li>
<li>Capa di: Capa de inyeccion de dependencias, encargada de suminisrar instancias de objetos en las clases sin construirlas, por simplicidad se hizo de dagger hilt</li>

<img  src='https://user-images.githubusercontent.com/29846058/129257397-d606977f-afdd-4c7a-8c39-8669a107ab42.png' width='60%'> 

### En qué consiste el principio de responsabilidad única? Cuál es su propósito?

Esta arquitectura sigue el principio de separación de problemas y delegacion de tareas. Por ejemplo los `fragments` y `activities` solo les será encargada toda la lógica correspondiente a interaccion con la UI y el sistema operativo, delegando a otro componente (`viewmodel`) la tarea de proporcionarle los datos.
Esto permite una aplicacion escalable y facil de mantener, ademas que el testeo de esta es mucho mas sencillo.

Se utilizó `Retrofit` para el consumo de la API REST y `Room` para simplificar las operaciones con la base de datos de Android. Algo importante tambien a mencionar es la utilización de `Dagger hilt` para la inyección de dependencias en todo el proyecto.

Por complicaciones de tiempo personal no hice plus que me hubiera gustado agregar como interacciones con botones y vista detalle de algunos componentes de vista para un uso mas completo de los datos suministrados.

## Preview

<img src='https://user-images.githubusercontent.com/29846058/131725937-4b630dff-0a8c-4014-8771-d7db96dfce00.jpeg' width='40%'>  
<img  src='https://user-images.githubusercontent.com/29846058/131726016-eef062b6-e95a-4fe7-a7fb-7f9617073f19.jpeg' width='40%'> 


## More
Espero les guste. Visita mi perfil en LinkedIn :bowtie:
:iphone: +57 301 4100407 
:email: angelmarrugoplata@gmail.com
:link: [LinkedIn](https://www.linkedin.com/in/angelmarrugo/)

don't forget to follow me, I'll be doing this more often :alien:


<img align='left' src='https://user-images.githubusercontent.com/29846058/127225908-1244c9ee-3d80-4f46-99e3-52b9dbeab291.gif' width='40%'>  
<img align='right' src='https://user-images.githubusercontent.com/29846058/127226289-08452ccf-9b37-4eed-876f-271ed2a0126b.gif' width='40%'>  
