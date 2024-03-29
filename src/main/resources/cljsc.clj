;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns com.edftwin.cljs)

(require '[cljs.closure :as closure])

(defn transform-cl-args
  [args]
  (let [source (first args)
        opts-string (apply str (interpose " " (rest args)))
        options (when (> (count opts-string) 1)
                  (try (read-string opts-string)
                       (catch Exception e (println e))))]
    {:source source :options (merge {:output-to :print} options)}))

(defn build
  [source opts]
  (let [args (transform-cl-args (list source opts))]
    (println (:source args)) 
    (println (:options args)) 
    (closure/build (:source args) (:options args))))
