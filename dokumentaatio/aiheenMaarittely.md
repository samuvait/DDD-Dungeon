## Pelin aiheen määrittely



**Aihe:** Luolassa tapahtuva peli. Toteutetaan peli, jossa seikkaillaan luolassa. Tähän kuuluu hirviöiden päihittäminen ja luolassa alas liikkuminen, mikä tapahtuu portaita alas kulkemalla.



Peli sisältää generaattorin joka luo uuden kerroksen aina kun pelaaja menee alas portaita. Pelaajan päihittäessä enemmän hirviöitä, hänen voimansa kasvaa ja hän pystyy päihittämään vahvempia hirviöitä. Peli tapahtuu 2D näkymässä ylhäältä katsottuna.

**Käyttäjät:** Pelaaja

**Pelaajan Toiminnot:**

- Liikkuminen
- Portaita alas meneminen
- Hirviöiden päihittäminen


### Rakennekuvaus:
LuolaGeneraattori luo luolan kerroksen Kaytavat ja Huoneet luokkien avulla. Liikkuminen aloittaa liikkumisen ja luo jokaiselle kerrokselle uudet Taisteleminen ja Viholliset luokat, jotka hoitavat vihollisten luonnin, sekä taistelemisen pelaajan ja vihollisten välillä. RuudukkoUI näyttää pelin kartan ja päivittää sitä pelaajan siirtojen mukaisesti, joita Kuuntelija kuuntelee.

#### Luokkakaavio

![Kuva luokkakaaviosta](/dokumentaatio/Luokkakaavio.png
)

#### Sekvenssikaavio kerroksen vaihdosta luolassa
![Kuva kerroksen vaihdosta](/dokumentaatio/KerroksenVaihto.png
)

#### Sekvenssikaavio uusien otusten luomisesta
![Kuva kerroksen vaihdosta](/dokumentaatio/uusienOtustenLuonti.png
)
