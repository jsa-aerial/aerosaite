
:cljs

(def regression-line
  (mapv (fn[m]
          (let [ds (-> m :dan-sleep roundit)
                dg (-> m :dan-grump roundit)]
            (assoc m :X ds :Y dg :dan-sleep ds :dan-grump dg)))
  [{:dan-sleep 4.0, :dan-grump 90.20926835578194}
   {:dan-sleep 4.1, :dan-grump 89.31559277383866}
   {:dan-sleep 4.2, :dan-grump 88.42191719189539}
   {:dan-sleep 4.3, :dan-grump 87.52824160995212}
   {:dan-sleep 4.4, :dan-grump 86.63456602800885}
   {:dan-sleep 4.5, :dan-grump 85.74089044606558}
   {:dan-sleep 4.6, :dan-grump 84.84721486412231}
   {:dan-sleep 4.7, :dan-grump 83.95353928217904}
   {:dan-sleep 4.8, :dan-grump 83.05986370023575}
   {:dan-sleep 4.9, :dan-grump 82.1661881182925}
   {:dan-sleep 5.0, :dan-grump 81.27251253634921}
   {:dan-sleep 5.1, :dan-grump 80.37883695440595}
   {:dan-sleep 5.2, :dan-grump 79.48516137246267}
   {:dan-sleep 5.3, :dan-grump 78.5914857905194}
   {:dan-sleep 5.4, :dan-grump 77.69781020857613}
   {:dan-sleep 5.5, :dan-grump 76.80413462663286}
   {:dan-sleep 5.6, :dan-grump 75.91045904468959}
   {:dan-sleep 5.7, :dan-grump 75.0167834627463}
   {:dan-sleep 5.8, :dan-grump 74.12310788080305}
   {:dan-sleep 5.9, :dan-grump 73.22943229885976}
   {:dan-sleep 6.0, :dan-grump 72.33575671691649}
   {:dan-sleep 6.1, :dan-grump 71.44208113497322}
   {:dan-sleep 6.2, :dan-grump 70.54840555302995}
   {:dan-sleep 6.300000000000001, :dan-grump 69.65472997108668}
   {:dan-sleep 6.4, :dan-grump 68.7610543891434}
   {:dan-sleep 6.5, :dan-grump 67.86737880720014}
   {:dan-sleep 6.6, :dan-grump 66.97370322525687}
   {:dan-sleep 6.7, :dan-grump 66.0800276433136}
   {:dan-sleep 6.800000000000001, :dan-grump 65.18635206137031}
   {:dan-sleep 6.9, :dan-grump 64.29267647942704}
   {:dan-sleep 7.0, :dan-grump 63.39900089748377}
   {:dan-sleep 7.1, :dan-grump 62.5053253155405}
   {:dan-sleep 7.2, :dan-grump 61.61164973359723}
   {:dan-sleep 7.300000000000001, :dan-grump 60.71797415165395}
   {:dan-sleep 7.4, :dan-grump 59.82429856971068}
   {:dan-sleep 7.5, :dan-grump 58.93062298776742}
   {:dan-sleep 7.6, :dan-grump 58.03694740582415}
   {:dan-sleep 7.7, :dan-grump 57.143271823880866}
   {:dan-sleep 7.800000000000001, :dan-grump 56.249596241937596}
   {:dan-sleep 7.9, :dan-grump 55.355920659994325}
   {:dan-sleep 8.0, :dan-grump 54.462245078051055}
   {:dan-sleep 8.100000000000001, :dan-grump 53.56856949610777}
   {:dan-sleep 8.2, :dan-grump 52.674893914164514}
   {:dan-sleep 8.3, :dan-grump 51.78121833222123}
   {:dan-sleep 8.4, :dan-grump 50.88754275027796}
   {:dan-sleep 8.5, :dan-grump 49.99386716833469}
   {:dan-sleep 8.600000000000001, :dan-grump 49.100191586391404}
   {:dan-sleep 8.7, :dan-grump 48.20651600444816}
   {:dan-sleep 8.8, :dan-grump 47.31284042250488}
   {:dan-sleep 8.9, :dan-grump 46.419164840561606}
   {:dan-sleep 9.0, :dan-grump 45.525489258618336}]))


(def residuals
  (mapv (fn[m] (assoc m :X (m :dan-sleep) :Y (m :dan-grump)))
  [{:residual -2.1263149640184693, 
    :baby-sleep 10.18, :day 1, :dan-sleep 7.59, :dan-grump 56.0,
    :prediction 58.12631496401847}
   {:residual 4.7334468982, :baby-sleep 11.66, :day 2, :dan-sleep 7.91, :dan-grump 60.0, :prediction 55.2665531018}
   {:residual 1.9786332783713618, :baby-sleep 7.92, :day 3, :dan-sleep 5.14, :dan-grump 82.0, :prediction 80.02136672162864}
   {:residual -2.0539042656865405, :baby-sleep 9.61, :day 4, :dan-sleep 7.71, :dan-grump 55.0, :prediction 57.05390426568654}
   {:residual 0.7412372402977496, :baby-sleep 9.75, :day 5, :dan-sleep 6.68, :dan-grump 67.0, :prediction 66.25876275970225}
   {:residual -0.42512427511081796, :baby-sleep 5.04, :day 6, :dan-sleep 5.99, :dan-grump 72.0, :prediction 72.42512427511082}
   {:residual 0.23573852764116054, :baby-sleep 10.45, :day 7, :dan-sleep 8.19, :dan-grump 53.0, :prediction 52.76426147235884}
   {:residual -1.7010172917915583, :baby-sleep 8.27, :day 8, :dan-sleep 7.19, :dan-grump 60.0, :prediction 61.70101729179156}
   {:residual 0.1757014302893154, :baby-sleep 6.06, :day 9, :dan-sleep 7.4, :dan-grump 60.0, :prediction 59.824298569710685}
   {:residual 3.847561658354479, :baby-sleep 7.09, :day 10, :dan-sleep 6.58, :dan-grump 71.0, :prediction 67.15243834164552}
   {:residual 4.043253634605534, :baby-sleep 11.68, :day 11, :dan-sleep 6.49, :dan-grump 72.0, :prediction 67.95674636539447}
   {:residual -4.922832645669672, :baby-sleep 6.13, :day 12, :dan-sleep 6.27, :dan-grump 65.0, :prediction 69.92283264566967}
   {:residual 1.2174054921118653, :baby-sleep 7.83, :day 13, :dan-sleep 5.95, :dan-grump 74.0, :prediction 72.78259450788813}
   {:residual 0.47313456571477275, :baby-sleep 5.6, :day 14, :dan-sleep 6.65, :dan-grump 67.0, :prediction 66.52686543428523}
   {:residual -2.671686830949085, :baby-sleep 6.03, :day 15, :dan-sleep 6.41, :dan-grump 66.0, :prediction 68.67168683094908}
   {:residual -0.3866272965037041, :baby-sleep 8.19, :day 16, :dan-sleep 6.33, :dan-grump 69.0, :prediction 69.3866272965037}
   {:residual 3.345270028913319, :baby-sleep 6.38, :day 17, :dan-sleep 6.3, :dan-grump 73.0, :prediction 69.65472997108668}
   {:residual 1.7380301570823278, :baby-sleep 11.11, :day 18, :dan-sleep 8.47, :dan-grump 52.0, :prediction 50.26196984291767}
   {:residual -0.522282175402907, :baby-sleep 5.51, :day 19, :dan-sleep 7.21, :dan-grump 61.0, :prediction 61.52228217540291}
   {:residual -5.66252031318443, :baby-sleep 6.69, :day 20, :dan-sleep 7.53, :dan-grump 53.0, :prediction 58.66252031318443}
   {:residual -0.46224507805105475, :baby-sleep 9.74, :day 21, :dan-sleep 8.0, :dan-grump 54.0, :prediction 54.462245078051055}
   {:residual 2.728863639317673, :baby-sleep 9.02, :day 22, :dan-sleep 7.35, :dan-grump 63.0, :prediction 60.27113636068233}
   {:residual 9.349853287795654, :baby-sleep 6.44, :day 23, :dan-sleep 6.86, :dan-grump 74.0, :prediction 64.65014671220435}
   {:residual 0.2866091072283652, :baby-sleep 9.43, :day 24, :dan-sleep 7.86, :dan-grump 56.0, :prediction 55.713390892771635}
   {:residual -0.5236583510697983, :baby-sleep 3.46, :day 25, :dan-sleep 4.86, :dan-grump 82.0, :prediction 82.5236583510698}
   {:residual -1.4975349734427539, :baby-sleep 6.32, :day 26, :dan-sleep 5.87, :dan-grump 72.0, :prediction 73.49753497344275}
   {:residual 8.112457249722034, :baby-sleep 7.95, :day 27, :dan-sleep 8.4, :dan-grump 59.0, :prediction 50.887542750277966}
   {:residual 1.9754261951559329, :baby-sleep 7.69, :day 28, :dan-sleep 6.93, :dan-grump 66.0, :prediction 64.02457380484407}
   {:residual -1.522282175402907, :baby-sleep 7.45, :day 29, :dan-sleep 7.21, :dan-grump 60.0, :prediction 61.52228217540291}
   {:residual 3.511631544321901, :baby-sleep 7.56, :day 30, :dan-sleep 6.99, :dan-grump 67.0, :prediction 63.4883684556781}
   {:residual -8.94299658874749, :baby-sleep 7.95, :day 31, :dan-sleep 8.17, :dan-grump 44.0, :prediction 52.94299658874749}
   {:residual -2.8027584509659675, :baby-sleep 11.61, :day 32, :dan-sleep 7.85, :dan-grump 53.0, :prediction 55.80275845096597}
   {:residual 6.077167354330328, :baby-sleep 4.7, :day 33, :dan-sleep 6.27, :dan-grump 76.0, :prediction 69.92283264566967}
   {:residual -7.563986237225457, :baby-sleep 8.52, :day 34, :dan-sleep 8.66, :dan-grump 41.0, :prediction 48.56398623722546}
   {:residual 4.548752347262138, :baby-sleep 4.7, :day 35, :dan-sleep 4.98, :dan-grump 86.0, :prediction 81.45124765273786}
   {:residual -10.637773111224277, :baby-sleep 8.32, :day 36, :dan-sleep 6.19, :dan-grump 60.0, :prediction 70.63777311122428}
   {:residual -5.671686830949085, :baby-sleep 9.38, :day 37, :dan-sleep 6.41, :dan-grump 63.0, :prediction 68.67168683094908}
   {:residual 6.2976065325415505, :baby-sleep 4.18, :day 38, :dan-sleep 4.84, :dan-grump 89.0, :prediction 82.70239346745845}
   {:residual -2.1308982229007896, :baby-sleep 5.98, :day 39, :dan-sleep 7.03, :dan-grump 61.0, :prediction 63.13089822290079}
   {:residual -0.5007420566581757, :baby-sleep 9.29, :day 40, :dan-sleep 7.66, :dan-grump 57.0, :prediction 57.500742056658176}
   {:residual 0.15874457042691148, :baby-sleep 6.01, :day 41, :dan-sleep 7.51, :dan-grump 59.0, :prediction 58.84125542957309}
   {:residual 4.822814456394326, :baby-sleep 10.54, :day 42, :dan-sleep 7.92, :dan-grump 60.0, :prediction 55.177185543605674}
   {:residual -5.389834379719133, :baby-sleep 11.78, :day 43, :dan-sleep 8.12, :dan-grump 48.0, :prediction 53.38983437971913}
   {:residual -6.198725662350398, :baby-sleep 11.6, :day 44, :dan-sleep 7.47, :dan-grump 53.0, :prediction 59.1987256623504}
   {:residual -4.55161263624538, :baby-sleep 11.35, :day 45, :dan-sleep 7.99, :dan-grump 50.0, :prediction 54.55161263624538}
   {:residual -5.340339975798813, :baby-sleep 5.63, :day 46, :dan-sleep 5.44, :dan-grump 72.0, :prediction 77.34033997579881}
   {:residual 3.9676358530581837, :baby-sleep 6.98, :day 47, :dan-sleep 8.16, :dan-grump 57.0, :prediction 53.032364146941816}
   {:residual 2.1417877105645147, :baby-sleep 6.03, :day 48, :dan-sleep 7.62, :dan-grump 60.0, :prediction 57.858212289435485}
   {:residual -3.497534973442754, :baby-sleep 4.66, :day 49, :dan-sleep 5.87, :dan-grump 70.0, :prediction 73.49753497344275}
   {:residual 0.47451074138166405, :baby-sleep 9.81, :day 50, :dan-sleep 9.0, :dan-grump 46.0, :prediction 45.525489258618336}
   {:residual 6.3081492259730965, :baby-sleep 12.07, :day 51, :dan-sleep 8.31, :dan-grump 58.0, :prediction 51.6918507740269}
   {:residual 2.0093399148807407, :baby-sleep 7.57, :day 52, :dan-sleep 6.71, :dan-grump 68.0, :prediction 65.99066008511926}
   {:residual -1.5561958951277077, :baby-sleep 11.35, :day 53, :dan-sleep 7.43, :dan-grump 58.0, :prediction 59.55619589512771}
   {:residual -2.229432298859763, :baby-sleep 5.47, :day 54, :dan-sleep 5.9, :dan-grump 71.0, :prediction 73.22943229885976}
   {:residual 2.184867948053956, :baby-sleep 8.29, :day 55, :dan-sleep 8.52, :dan-grump 52.0, :prediction 49.815132051946044}
   {:residual 1.9323459576664845, :baby-sleep 6.8, :day 56, :dan-sleep 6.03, :dan-grump 74.0, :prediction 72.06765404233352}
   {:residual -1.8073417098482878, :baby-sleep 10.63, :day 57, :dan-sleep 7.29, :dan-grump 59.0, :prediction 60.80734170984829}
   {:residual -1.5392390352653038, :baby-sleep 8.59, :day 58, :dan-sleep 7.32, :dan-grump 59.0, :prediction 60.539239035265304}
   {:residual 2.5285884041842905, :baby-sleep 7.82, :day 59, :dan-sleep 6.88, :dan-grump 67.0, :prediction 64.47141159581571}
   {:residual -3.3696704366413, :baby-sleep 7.18, :day 60, :dan-sleep 6.22, :dan-grump 67.0, :prediction 70.3696704366413}
   {:residual -2.9352062466497344, :baby-sleep 8.29, :day 61, :dan-sleep 6.94, :dan-grump 61.0, :prediction 63.935206246649734}
   {:residual 0.6903666607105521, :baby-sleep 11.08, :day 62, :dan-sleep 7.01, :dan-grump 64.0, :prediction 63.30963333928945}
   {:residual -0.6116497335972326, :baby-sleep 6.46, :day 63, :dan-sleep 7.2, :dan-grump 61.0, :prediction 61.61164973359723}
   {:residual -8.654729971086681, :baby-sleep 3.25, :day 64, :dan-sleep 6.3, :dan-grump 61.0, :prediction 69.65472997108668}
   {:residual 5.972219111940511, :baby-sleep 9.74, :day 65, :dan-sleep 8.72, :dan-grump 54.0, :prediction 48.02778088805949}
   {:residual 5.929138874451056, :baby-sleep 8.75, :day 66, :dan-sleep 7.82, :dan-grump 62.0, :prediction 56.070861125548944}
   {:residual -1.2110992633304676, :baby-sleep 11.75, :day 67, :dan-sleep 8.14, :dan-grump 52.0, :prediction 53.21109926333047}
   {:residual 3.013923173763054, :baby-sleep 9.31, :day 68, :dan-sleep 7.27, :dan-grump 64.0, :prediction 60.986076826236946}
   {:residual -1.080027643313585, :baby-sleep 7.73, :day 69, :dan-sleep 6.7, :dan-grump 65.0, :prediction 66.08002764331358}
   {:residual 6.516214803204221, :baby-sleep 8.68, :day 70, :dan-sleep 7.55, :dan-grump 65.0, :prediction 58.48378519679578}
   {:residual -3.003033686099343, :baby-sleep 9.77, :day 71, :dan-sleep 7.38, :dan-grump 57.0, :prediction 60.00303368609934}
   {:residual 2.124830850702118, :baby-sleep 9.71, :day 72, :dan-sleep 7.73, :dan-grump 59.0, :prediction 56.87516914929788}
   {:residual 0.5872493258692515, :baby-sleep 4.17, :day 73, :dan-sleep 5.32, :dan-grump 79.0, :prediction 78.41275067413075}
   {:residual -2.713390892771635, :baby-sleep 10.18, :day 74, :dan-sleep 7.86, :dan-grump 53.0, :prediction 55.713390892771635}
   {:residual -2.207892180115053, :baby-sleep 9.28, :day 75, :dan-sleep 6.35, :dan-grump 67.0, :prediction 69.20789218011505}
   {:residual -1.4159577573461704, :baby-sleep 7.23, :day 76, :dan-sleep 7.11, :dan-grump 61.0, :prediction 62.41595775734617}
   {:residual 4.749027582395513, :baby-sleep 6.38, :day 77, :dan-sleep 5.45, :dan-grump 82.0, :prediction 77.25097241760449}
   {:residual 11.750403758062397, :baby-sleep 9.2, :day 78, :dan-sleep 7.8, :dan-grump 68.0, :prediction 56.2495962419376}
   {:residual 4.762777359042481, :baby-sleep 8.2, :day 79, :dan-sleep 7.13, :dan-grump 67.0, :prediction 62.23722264095752}
   {:residual 2.665619458750392, :baby-sleep 10.16, :day 80, :dan-sleep 8.35, :dan-grump 54.0, :prediction 51.33438054124961}
   {:residual -11.024573804844067, :baby-sleep 8.95, :day 81, :dan-sleep 6.93, :dan-grump 53.0, :prediction 64.02457380484407}
   {:residual -0.77342799012348, :baby-sleep 6.8, :day 82, :dan-sleep 7.07, :dan-grump 62.0, :prediction 62.77342799012348}
   {:residual 1.436013762774543, :baby-sleep 8.34, :day 83, :dan-sleep 8.66, :dan-grump 50.0, :prediction 48.56398623722546}
   {:residual -0.4682045126002663, :baby-sleep 6.25, :day 84, :dan-sleep 5.09, :dan-grump 80.0, :prediction 80.46820451260027}
   {:residual 8.923179439901844, :baby-sleep 6.75, :day 85, :dan-sleep 4.91, :dan-grump 91.0, :prediction 82.07682056009816}
   {:residual -1.1308982229007896, :baby-sleep 9.09, :day 86, :dan-sleep 7.03, :dan-grump 62.0, :prediction 63.13089822290079}
   {:residual 0.7797342189048777, :baby-sleep 10.42, :day 87, :dan-sleep 7.02, :dan-grump 64.0, :prediction 63.22026578109512}
   {:residual -0.4113744984638501, :baby-sleep 8.89, :day 88, :dan-sleep 7.67, :dan-grump 57.0, :prediction 57.41137449846385}
   {:residual 0.878268294863858, :baby-sleep 9.43, :day 89, :dan-sleep 8.15, :dan-grump 54.0, :prediction 53.12173170513614}
   {:residual -1.4081674152484283, :baby-sleep 6.79, :day 90, :dan-sleep 5.88, :dan-grump 72.0, :prediction 73.40816741524843}
   {:residual 3.1619516536423333, :baby-sleep 6.91, :day 91, :dan-sleep 5.72, :dan-grump 78.0, :prediction 74.83804834635767}
   {:residual -3.4374978760909016, :baby-sleep 6.05, :day 92, :dan-sleep 6.66, :dan-grump 63.0, :prediction 66.4374978760909}
   {:residual -5.739514270398686, :baby-sleep 6.32, :day 93, :dan-sleep 6.85, :dan-grump 59.0, :prediction 64.73951427039869}
   {:residual -2.1785617192725653, :baby-sleep 8.62, :day 94, :dan-sleep 5.57, :dan-grump 74.0, :prediction 76.17856171927257}
   {:residual -3.842631605239987, :baby-sleep 7.84, :day 95, :dan-sleep 5.16, :dan-grump 76.0, :prediction 79.84263160523999}
   {:residual 0.49788176767492587, :baby-sleep 5.89, :day 96, :dan-sleep 5.31, :dan-grump 79.0, :prediction 78.50211823232507}
   {:residual -5.517698916520587, :baby-sleep 9.77, :day 97, :dan-sleep 7.77, :dan-grump 51.0, :prediction 56.51769891652059}
   {:residual 4.1234546750352195, :baby-sleep 6.97, :day 98, :dan-sleep 5.38, :dan-grump 82.0, :prediction 77.87654532496478}
   {:residual -8.220265781095122, :baby-sleep 6.56, :day 99, :dan-sleep 7.02, :dan-grump 55.0, :prediction 63.22026578109512}
   {:residual 5.685783401828232, :baby-sleep 7.93, :day 100, :dan-sleep 6.45, :dan-grump 74.0, :prediction 68.31421659817177}]))