Feature: Mac Secimi

  Scenario: Login Ol ve Voleybol Maci Sec
    Given Uygulamayi ac
    When Giris ekrani acilinca
      And Kullanici bilgileri girilince
      And Giris yap butonuna tiklaninca
    Then Giris yap butonu kaybolur
      And Kullanici adi gorulur
      And Bakiye gorulur
    When Tum sporlara tiklaninca
      And Voleybol secilince
      And İlk macin ms alani secilince
    Then Oran bilgileri kontrol edilir
      And Mac sayisi kontrol edilir
    When Kupon detayi acilinca
      Then Mac adi bilgileri kontrol edilir
