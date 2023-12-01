API Endpoints:
1. Kategorier:
   GET: Hämta alla kategorier eller en specifik kategori.
        GET /api/categories 				<- Alla kategorier
        GET /api/categories?category=... 	<- Hämtar specifik kategori

   POST: Skapa en ny kategori (kräver admin roll). Namnet får inte kollidera med en befintlig kategori.
        POST /api/categories med en json body (kollar så att namnet inte redan finns)

   2. Platser:
      GET: 
        Hämta alla publika platser eller en specifik publik plats (för anonyma användare).
            GET /api/places 					<- Alla platser för både anonyma och authade användare
            GET /api/places/{id}				<- Hämtar specifik publik plats för anonyma användare eller egen privat/publik plats för basic authade användare
        
        Hämta alla publika platser inom en specifik kategori.
            GET /api/places?category=...		<- Hämtar alla publika platser inom kategorin
        
        Hämta alla platser (både publika och privata) som tillhör den inloggade användaren.
            GET /api/places?onlyMyPlaces=true	<- Basic auth krävs
        
        Hämta alla platser inom en viss yta (radie från ett centrum eller hörn på en kvadrat).
            GET /api/places/{id}?radius=...		<- Hämtar alla platser (bara publika om man är anonym, men bara om {id} är publik plats. Alla ens egna + publika platser inom radien av plats med {id} om man är authad)
      
      POST: Skapa en ny plats (kräver inloggning).
            POST /api/places med json body av en PlaceServiceDto		<- Skapar
      
      PUT/PATCH: Uppdatera en befintlig plats (kräver inloggning).
            PUT /api/places med json body av en PlaceServiceDtoPut		<- Updaterar
      
      DELETE: Ta bort en befintlig plats (kräver inloggning).
            DELETE /api/places/{id}				<- Tar bort, bara om man är användaren för platsen