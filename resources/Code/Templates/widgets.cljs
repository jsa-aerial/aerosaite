

:cljs

(def use-defaults? :aerial.hanami.common/use-defaults?)

(defn xframes [frame-map & {:keys [defaults?] :or {defaults? false}}]
  (let [submap (merge {use-defaults? defaults?} frame-map)]
    (hc/xform
     (hc/get-default :USERDATA)
     submap)))

(defn xc
  ([comps submap]
   (let [defaults? (submap :defaults? true)]
     (hc/xform comps (merge {use-defaults? defaults?} submap))))
  ([comps k v & kvs]
   (let [submap (->> (cons k (cons v kvs))
                  (partition-all 2) (mapv vec) (into {}))]
     (xc comps submap))))

(def sidebar
  [['p {:style {:width "50px" :min-width "50px"}}]
   ['v-box :children
    :components]])


(def top
  [['gap :size :TOPGAP]
   ['v-box :children
    :components]])

(def bottom
  [['gap :size :BOTTOM-GAP]
   ['h-box :children
    :components]])


(def basicButton
  '[button
    :label :BBUTTON-label
    :tooltip :BBUTTON-tooltip
    :tooltip-position :below-center
    :disabled? :BBUTTON-disabled?
    :on-click :BBUTTON-on-click
    :class :BBUTTON-class])

(def iconButton
  '[md-icon-button
    :md-icon-name :IBUTTON-md-icon-name
    :tooltip :IBUTTON-tooltip
    :tooltip-position :IBUTTON-tooltip-position
    :disabled? :IBUTTON-disabled?
    :on-click :IBUTTON-on-click
    :class :IBUTTON-class])

(def ciconButton
  '[md-circle-icon-button
    :md-icon-name :CIBUTTON-md-icon-name
    :tooltip :CIBUTTON-tooltip
    :tooltip-position :CIBUTTON-tooltip-position
    :disabled? :CIBUTTON-disabled?
    :size :CIBUTTON-size
    :on-click :CIBUTTON-on-click
    :class :CIBUTTON-class])


(def checkBox
  '[checkbox
   :label :CHECKBOX-label
   :model :CHECKBOX-model
   :disabled? :CHECKBOX-disabled?
   :label-style :CHECKBOX-label-style
   :on-change :CHECKBOX-on-change])

(def radioButton
  '[radio-button
   :label :RBUTTON-label
   :value :RBUTTON-value
   :model :RBUTTON-model
   :label-style :RBUTTON-label-style
   :on-change :RBUTTON-on-change])


(def sliderInput
  '[v-box :children
    [[label :label [:span.bold :SLIDER-LBL]]
     [h-box :children
      [[label :label :MINLBL]
       [slider
        :model :SLIDER-MODEL
        :min :MIN, :max :MAX, :step :STEP
        :width :SLIDER-WIDTH
        :on-change :SLIDER-CHG-FNSYM]
       [label :label :MAXLBL]]]]])


(def singleDropdown
  ['v-box
   :children
   [['label :label [:span.bold :DROPDOWN-LBL]]
    ['single-dropdown
     :choices :CHOICES
     :on-change :DROPDOWN-CHG-FNSYM
     :model :DROPDOWN-MODEL
     :placeholder :PLACEHOLDER
     :width :DROPDOWN-WIDTH]]])

(def selectionList
  ['v-box
   :children
   [['label :label [:span.bold :SELECTION-LBL]]
    ['selection-list
     :choices :CHOICES
     :on-change :SELECTION-CHG-FNSYM
     :model :SELECTION-MODEL
     :multi-select? :MULTI?
     :required? :REQUIRED?
     :height :SELECTION-HEIGHT
     :width :SELECTION-WIDTH]]])


(def textInput
  '[input-text
    :model :INPUT-MODEL
    :width "60px", :height "26px"
    :on-change :INPUT-CHG-FNSYM])

(def markdown
  ['md {:style {:font-size "24px" :width "1000px"}}
   :MDTXT])




(hc/update-defaults
 :BINNED-ENCODING
 {:x {:field :bin_start :type :quantitative
      :bin {:binned true :step 2}
      :title :XTITLE}
  :x2 {:field :bin_end}
  :y {:field :Y :type :quantitative}}
 :TOPGAP "500px" :MDTXT "Your text here"
 :BOTTOM-GAP "500px"
 :SLIDER-LBL "**Slider Lable**"
 :SLIDER-WIDTH "200px" :STEP 1.0 
 :MIN 0 :MINLBL #(-> % :MIN str)
 :MAX 10 :MAXLBL #(-> % :MAX str)
 :DROPDOWN-MODEL nil :DROPDOWN-WIDTH "150px"
 :SELECTION-MODEL #{} :SELECTION-WIDTH "150px" :SELECTION-HEIGHT "200px"
 :INPUT-CHG-FNSYM #(-> % :SLIDER-CHG-FNSYM)
 :INPUT-MODEL #(-> % :SLIDER-MODEL))
(hc/add-defaults :RBUTTON-label-style hc/RMV)





