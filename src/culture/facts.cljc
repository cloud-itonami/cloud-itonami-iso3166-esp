(ns culture.facts
  "Country-level regional-culture catalog for Spain (ESP) -- national
  dishes, protected products, beverages, crafts, festivals and heritage
  sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). Sibling namespace to
  `marketentry.facts` / `statute.facts` (ADR-2607141700); city-level
  counterparts live in the cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"ESP"
   [{:culture/id "esp.dish.paella"
     :culture/name "Paella"
     :culture/country "ESP"
     :culture/kind :dish
     :culture/summary "Rice dish originally from the Valencian Community of Spain, regarded as one of the community's identifying symbols."
     :culture/url "https://en.wikipedia.org/wiki/Paella"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "esp.dish.spanish-omelette"
     :culture/name "Spanish omelette"
     :culture/name-local "Tortilla española"
     :culture/country "ESP"
     :culture/kind :dish
     :culture/summary "Omelette made with eggs and potatoes, often including onions, one of the most popular dishes in Spanish cuisine."
     :culture/url "https://en.wikipedia.org/wiki/Spanish_omelette"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "esp.dish.gazpacho"
     :culture/name "Gazpacho"
     :culture/country "ESP"
     :culture/kind :dish
     :culture/summary "Cold soup of blended raw vegetables originating in the southern Iberian Peninsula, widely eaten in Spain (particularly Andalusia) and Portugal in summer."
     :culture/url "https://en.wikipedia.org/wiki/Gazpacho"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "esp.beverage.sangria"
     :culture/name "Sangria"
     :culture/name-local "Sangría"
     :culture/country "ESP"
     :culture/kind :beverage
     :culture/summary "Alcoholic beverage of red wine and chopped fruit originating from Spain and Portugal; under 2014 EU regulations only products made in Spain or Portugal can be labeled 'sangria'."
     :culture/url "https://en.wikipedia.org/wiki/Sangria"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "esp.beverage.rioja"
     :culture/name "Rioja DOCa"
     :culture/country "ESP"
     :culture/kind :beverage
     :culture/summary "Spanish wine appellation holding DOCa status, the highest category in Spanish wine regulation, spanning La Rioja, Navarre and Álava and known for Tempranillo-based reds."
     :culture/url "https://en.wikipedia.org/wiki/Rioja_DOCa"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "esp.product.jamon-iberico"
     :culture/name "Jamón ibérico"
     :culture/country "ESP"
     :culture/kind :product
     :culture/summary "Cured pork product produced in Spain and Portugal, with Spanish protected designations of origin such as DOP Guijuelo and DOP Jabugo; about 60% of production comes from the Guijuelo region."
     :culture/url "https://en.wikipedia.org/wiki/Jam%C3%B3n_ib%C3%A9rico"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "esp.product.manchego"
     :culture/name "Manchego"
     :culture/country "ESP"
     :culture/kind :product
     :culture/summary "Cheese made in the La Mancha region of Spain from the milk of sheep of the Manchega breed, holding EU Protected Designation of Origin status."
     :culture/url "https://en.wikipedia.org/wiki/Manchego"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "esp.festival.la-tomatina"
     :culture/name "La Tomatina"
     :culture/country "ESP"
     :culture/kind :festival
     :culture/summary "Spanish festival in Buñol held annually on the last Wednesday of August, where participants throw tomatoes at each other in the world's largest food fight."
     :culture/url "https://en.wikipedia.org/wiki/La_Tomatina"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "esp.festival.san-fermin"
     :culture/name "Festival of San Fermín"
     :culture/name-local "Sanfermines"
     :culture/country "ESP"
     :culture/kind :festival
     :culture/summary "Week-long traditional celebration held annually in Pamplona, Navarre, Spain, whose most famous event is the running of the bulls on 7-14 July."
     :culture/url "https://en.wikipedia.org/wiki/Festival_of_San_Ferm%C3%ADn"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "esp.heritage.alhambra"
     :culture/name "Alhambra"
     :culture/country "ESP"
     :culture/kind :heritage
     :culture/summary "Palatine city and fortress complex in Granada, Spain, a major example of Islamic architecture designated a UNESCO World Heritage Site in 1984."
     :culture/url "https://en.wikipedia.org/wiki/Alhambra"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-esp culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "ESP"))
                 " ESP entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
