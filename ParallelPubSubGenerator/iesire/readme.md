# Evaluare generator publicatii & subscriptii

Acest document prezinta rezultatele obtinute in urma rularii generatorului de date
pentru publicatii si subscriptii, folosind diferite grade de paralelizare.

## Configuratie

- Tip paralelizare: **thread-uri (ExecutorService)**
- Factori de paralelism testati: **[1, 4]**
- Numar publicatii: **50000**
- Numar subscriptii: **50000**
- Procesor: _AMD Ryzen 7 4800H with Radeon Graphics_

## Rezultate masurate

| Fire | Publicatii (ms) | Subscriptii (ms) | Total (ms) |
|------|-----------------|------------------|------------|
| 1 | 84 | 437 | 850 |
| 4 | 11 | 90 | 257 |

## Observatii

- Cresterea numarului de thread-uri reduce timpul total de executie.
- Generarea subscriptiilor este mai costisitoare decat cea a publicatiilor,
  datorita planificarii distributiei campurilor si operatorilor.
- Exista un compromis intre overhead-ul de paralelizare si castigul de performanta.

## Concluzie

Utilizarea paralelizarii prin thread-uri imbunatateste semnificativ performanta
generatorului, in special pentru volume mari de date.
