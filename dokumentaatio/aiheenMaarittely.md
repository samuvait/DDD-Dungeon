## Pelin aiheen määrittely



**Aihe:** Luolassa tapahtuva peli. Toteutetaan peli, jossa seikkaillaan luolassa. Tähän kuuluu hirviöiden päihittäminen ja luolassa alas liikkuminen, mikä tapahtuu portaita alas kulkemalla.



Peli sisältää generaattorin joka luo uuden kerroksen aina kun pelaaja menee alas portaita. Pelaajan päihittäessä enemmän hirviöitä, hänen voimansa kasvaa ja hän pystyy päihittämään vahvempia hirviöitä. Peli tapahtuu 2D näkymässä ylhäältä katsottuna.

**Käyttäjät:** Pelaaja

**Pelaajan Toiminnot:**

- Liikkuminen
- Portaita alas meneminen
- Hirviöiden päihittäminen


### Rakennekuvaus:
Main metodi aloittaa graafisen käyttöliittymän toiminnan RuudukkoUI luokan avulla. RuudukkoUI luo LuolaGeneraattorin, joka luo luolan kerroksen Kaytavat ja Huoneet luokkien avulla. Huoneet luokka luo huoneet ja sijoittaa ne kartalle, jonka LuolaGeneraattori on luonut. Huoneet on kuvattu Huone luokan avulla, joka sisältää niiden tärkeimmät tiedot. Kaytavat luokka sijoittaa käytävät luotujen huoneiden välille.

RuudukkoUI luo sen jälkeen Liikkuminen luokan, joka aloittaa liikkumisen ja luo jokaiselle kerrokselle uudet Taisteleminen ja Viholliset luokat, jotka hoitavat vihollisten luonnin, sekä taistelemisen pelaajan ja vihollisten välillä. Viholliset luokka luo Otus luokan olioita, jotka sijoitetaan kartalle. Liikkuminen luokka käyttää Pelaaja luokka kuvaamaan pelaajaa ja se käyttää Koordinaatteja pelaajan sijainnin kuvaamiseen kartalla. Taisteleminen tarkastaa voiko Otus luokan oliot hyökätä pelajaan ja hyökkääkö pelaaja siirrollaan otuksiin.

RuudukkoUI näyttää pelin kartan ja päivittää sitä pelaajan siirtojen mukaisesti, joita Kuuntelija kuuntelee. RuudukkoUI myös pitää kirjaa pelaajan edistymisestä ja näyttää viestejä, esim. jos pelaaja kuolee tai voittaa pelin.



#### Luokkakaavio

![Kuva luokkakaaviosta](/dokumentaatio/Luokkakaavio.png
)

#### Sekvenssikaavio kerroksen vaihdosta luolassa
![Kuva kerroksen vaihdosta](/dokumentaatio/KerroksenVaihto.png
)

#### Sekvenssikaavio uusien otusten luomisesta
![Kuva kerroksen vaihdosta](/dokumentaatio/uusienOtustenLuonti.png
)
