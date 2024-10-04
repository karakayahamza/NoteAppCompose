# NotUygulaması

**NotUygulaması**, Kotlin programlama dilinde geliştirilmiş ve Android platformunda çalışan bir not alma uygulamasıdır. Kullanıcıların günlük notlarını kolayca kaydedebilmeleri için tasarlanmıştır. Uygulama, kullanıcıların farklı notlar ekleyip düzenleyerek, önemli bilgilerini anlık olarak kaydedebilmelerine olanak tanır. Ayrıca, notları sıralama özelliği ile kullanıcıların notlarına daha düzenli bir şekilde ulaşmalarını sağlar.

## Uygulama Teknolojileri ve Mimari Yapı

Uygulamamda kullandığım teknolojiler ve yaklaşımlar:

- **Kotlin:** Projemin temelini oluşturan programlama dili. Kotlin ile daha okunabilir ve verimli kod yazmak benim için önemli.
  
- **MVVM Mimarisi:** Kullanıcı arayüzü ile veri arasında net bir ayrım sağlıyor. Bu sayede kodum daha test edilebilir hale geliyor ve uygulama mimarim temiz kalıyor.

- **ViewModel:** Kullanıcı arayüzü verilerimi yönetmek için kullanıyorum. Bu sayede yaşam döngüsü ile ilgili sorunlarım azalıyor.

- **Dagger Hilt:** Bağımlılıkları yönetmek için tercih ettiğim bir kütüphane. Uygulamamdaki bağımlılıkları daha kolay ve anlaşılır bir şekilde organize ediyorum.

- **Flow:** Asenkron veri akışlarını yönetmek için Flow kullanıyorum. Bu yapı, veri akışlarını gözlemleyip yönetmeyi oldukça kolaylaştırıyor.

- **Compose:** Modern ve esnek bir kullanıcı arayüzü geliştirme kütüphanesi. Kullanıcı deneyimini zenginleştirmek için harika bir araç.

- **Room DAO:** Veritabanı işlemlerini yönetmek için kullandığım kütüphane. Notlarımı kalıcı bir şekilde saklamak ve yönetmek için etkili bir çözüm.

- **Navigation Compose:** Kullanıcı arayüzünde sayfalar arası geçişleri kolaylaştırmak için kullandığım bir yapı. Uygulamamda kullanıcı deneyimini artırmak için bu yapıyı entegre ettim.

- **Coroutine:** Asenkron programlama için kullandığım yapılar. Uygulamamdaki arka plan işlemlerini yönetmeyi kolaylaştırıyor.

## Özellikler

- Not ekleme, düzenleme ve silme
- Notları sıralama
- Günlük notları kaydedebilme ve anlık erişim
