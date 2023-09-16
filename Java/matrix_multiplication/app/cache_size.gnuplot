set term pdfcairo
set output 'cache_size_plot.pdf'
set datafile separator ','
set logscale x 2;set xtics 4,4,1048576 rotate by -45 left
set format x '2^{%L}'
# set xtics 1
set key left top inside
set ylabel 'ns per look up'
set xlabel 'Size of array, n '
#set title 'Time per random  look up in a one dimensional double array'
    plot 'CacheSize.csv' using 4:5 with linespoints title 'Look up time [ns]'
