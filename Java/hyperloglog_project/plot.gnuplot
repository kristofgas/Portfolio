set term pdfcairo
set output 'plot_hashmap_analysis.pdf'
set datafile separator ','
set logscale y
set xlabel 'P-values'
set ylabel 'Frequency(n)'
    plot 'somefile.csv ' using 1:2 with lines \