(def project 'origen)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources" "src"}
          :source-paths   #{"test"}
          :dependencies   '[[org.clojure/clojure "1.8.0"]
                            [adzerk/boot-test "RELEASE" :scope "test"]])

(task-options!
 aot {:namespace   #{'origen.core}}
 pom {:project     "origen"
      :version     "0.0.1"
      :description "Semi-static web publishing platform."
      :url         "https://github.com/timotheosh/origen"
      :scm         {:url "https://github.com/timotheosh/origen"}
      :license     {"MIT License"
                    "http://www.opensource.org/licenses/MIT"}}
 repl {:init-ns    'origen.core}
 jar {:main        'origen.core
      :file        (str "origen-" version "-standalone.jar")})

(deftask build
  "Build the project locally as a JAR."
  [d dir PATH #{str} "the set of directories to write to (target)."]
  (let [dir (if (seq dir) dir #{"target"})]
    (comp (aot) (pom) (uber) (jar) (target :dir dir))))

(deftask run
  "Run the project."
  [a args ARG [str] "the arguments for the application."]
  (with-pass-thru fs
    (require '[origen.core :as app])
    (apply (resolve 'app/-main) args)))

(require '[adzerk.boot-test :refer [test]])
