(ns marketentry.facts "Spain market-entry catalog.")
(def catalog
  {"ESP" {:name "Spain"
          :owner-authority "DG Patrimonio / Plataforma de Contratación del Sector Público"
          :legal-basis "Ley 9/2017 de Contratos del Sector Público"
          :national-spec "PLACSP registration + NIF/CIF"
          :provenance "https://contrataciondelestado.es/"
          :required-evidence ["NIF/CIF record"
                              "PLACSP registration record"
                              "Mercantile registry extract"
                              "Authorized-representative record"]
          :rep-owner-authority "contracting authorities / PLACSP"
          :rep-legal-basis "EU establishment or authorized representative for many procedures"
          :rep-provenance "https://contrataciondelestado.es/"
          :corporate-number-owner-authority "AEAT / Registro Mercantil"
          :corporate-number-legal-basis "NIF/CIF"
          :corporate-number-provenance "https://www.agenciatributaria.es/"}
   "USA" {:name "United States" :owner-authority "GSA/SAM.gov" :legal-basis "FAR"
          :national-spec "SAM.gov" :provenance "https://sam.gov/"
          :required-evidence ["EIN record" "SAM.gov registration record" "State business registration record" "SAM UEI verification record"]}
   "DEU" {:name "Germany" :owner-authority "e-Vergabe" :legal-basis "GWB/VgV"
          :national-spec "e-Vergabe" :provenance "https://www.evergabe-online.de/"
          :required-evidence ["Handelsregister extract" "e-Vergabe registration record" "USt-IdNr record" "Authorized-representative record"]}
   "ITA" {:name "Italy" :owner-authority "ANAC / MEPA" :legal-basis "Codice dei contratti pubblici"
          :national-spec "MEPA/ANAC" :provenance "https://www.anticorruzione.it/"
          :required-evidence ["Partita IVA record" "MEPA registration" "Registro imprese extract" "Authorized-representative record"]}})

(defn spec-basis [iso3] (get catalog iso3))
(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s) missing (remove catalog iso3s)]
     {:requested (count iso3s) :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note "R0 catalog seed"})))
(defn required-evidence-satisfied? [iso3 submitted]
  (when-let [{:keys [required-evidence]} (spec-basis iso3)]
    (= (count required-evidence) (count (filter (set submitted) required-evidence)))))
(defn evidence-checklist [iso3] (:required-evidence (spec-basis iso3) []))
(defn rep-spec-basis [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:rep-owner-authority sb)
      (select-keys sb [:rep-owner-authority :rep-legal-basis :rep-provenance]))))
(defn corporate-number-spec-basis [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:corporate-number-owner-authority sb)
      (select-keys sb [:corporate-number-owner-authority :corporate-number-legal-basis :corporate-number-provenance]))))
