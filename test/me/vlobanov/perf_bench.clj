(ns me.vlobanov.perf-bench
  (:use me.vlobanov.mustache
        clojure.test
        stencil.core)
  (:require [stencil.loader]))

(def template-str "
<!DOCTYPE html>
<html>
  <head>
    <title>{{ title }}</title>
  </head>
  <body>
    <ul>
      {{#list}}<li>id: {{id}}, name: {{name}}</li>
      {{/list}}
    </ul>
  </body>
</html>
")

(deftemplate template template-str)

(def data {:title "mustache clojure implementation"
           :list (concat [{:id "https://github.com/davidsantiago/stencil"
                           :name "stencil"}
                          {:id "https://github.com/shenfeng/mustache.clj"
                           :name "shenfeng"}]
                         (map (fn [id]
                                {:id id
                                 :name (str "test-test" id)}) (range 1 10)))})


(stencil.loader/register-template "hithere" template-str)

(defn bench [&{:keys [loops] :or {loops 100000}}]
  (println "warn up jvm")
  (dotimes [i loops]
    (template data)
    (render-file "hithere" data))

  (println "bench stencil")
  ;; from a test run 1402.559462 msecs
  (time (dotimes [i loops]
          (render-file "hithere" data)))

  ;; from a tes run 638.368631 msecs
  (println "bench mustache.clj")
  (time (dotimes [i loops]
          ;; bench mustache.clj
          (template data))))

;;; use `lein test` to run it
(deftest testtest
  (bench))
