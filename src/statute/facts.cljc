(ns statute.facts
  "General-law compliance catalog for Spain (ESP) -- extends this
  repo's existing `marketentry.facts` (public-procurement market-entry
  only, narrow scope) with a second, orthogonal catalog of statutes a
  company generally must track for compliance. Mirrors
  cloud-itonami-iso3166-jpn/-usa/-gbr/-deu/-fra/-can/-aus/-kor/-nld/-ita's
  `statute.facts` (ADR-2607141700, cloud-itonami-compliance-fact-federation).

  Every entry cites an OFFICIAL boe.es (Boletín Oficial del Estado,
  Spain's Official State Gazette) URL -- never fabricated. A law not in
  this table has NO spec-basis, full stop; extend `catalog`, do not
  invent an id/url. Title for every entry below was directly WebFetch-
  verified against the live boe.es page on 2026-07-15 (rendered
  cleanly).")

(def catalog
  "iso3 -> vector of statute entries."
  {"ESP"
   [{:statute/id "esp.ley-sociedades-de-capital"
     :statute/title "Real Decreto Legislativo 1/2010, de 2 de julio, por el que se aprueba el texto refundido de la Ley de Sociedades de Capital"
     :statute/jurisdiction "ESP"
     :statute/kind :law
     :statute/law-number "BOE-A-2010-10544"
     :statute/url "https://www.boe.es/buscar/act.php?id=BOE-A-2010-10544"
     :statute/url-provenance :official-boe
     :statute/enacted-date "2010-07-02"
     :statute/retrieved-at "2026-07-15"
     :statute/topic #{:corporate-governance :incorporation}}
    {:statute/id "esp.lopdgdd"
     :statute/title "Ley Orgánica 3/2018, de 5 de diciembre, de Protección de Datos Personales y garantía de los derechos digitales"
     :statute/jurisdiction "ESP"
     :statute/kind :law
     :statute/law-number "BOE-A-2018-16673"
     :statute/url "https://www.boe.es/buscar/act.php?id=BOE-A-2018-16673"
     :statute/url-provenance :official-boe
     :statute/enacted-date "2018-12-05"
     :statute/retrieved-at "2026-07-15"
     :statute/topic #{:data-protection :privacy}}
    {:statute/id "esp.estatuto-de-los-trabajadores"
     :statute/title "Real Decreto Legislativo 2/2015, de 23 de octubre, por el que se aprueba el texto refundido de la Ley del Estatuto de los Trabajadores"
     :statute/jurisdiction "ESP"
     :statute/kind :law
     :statute/law-number "BOE-A-2015-11430"
     :statute/url "https://www.boe.es/buscar/doc.php?id=BOE-A-2015-11430"
     :statute/url-provenance :official-boe
     :statute/enacted-date "2015-10-23"
     :statute/retrieved-at "2026-07-15"
     :statute/topic #{:labor :employment}}]})

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
      :note (str "cloud-itonami-iso3166-esp statute.facts Wave 0 (ADR-2607141700): "
                 (count (get catalog "ESP")) " ESP statutes seeded with an "
                 "official boe.es citation. Extend "
                 "`statute.facts/catalog`, never fabricate a law-id or URL.")})))

(defn by-topic [iso3 topic]
  (filterv #(contains? (:statute/topic %) topic) (spec-basis iso3)))
