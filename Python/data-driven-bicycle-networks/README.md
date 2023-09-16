# A data driven approach for growing bicycle networks, based on network structure, population density and bicycle traffic flow

This is the source code for the master thesis 'A data driven approach for growing bicycle networks, based on network structure, population density and bicycle traffic flow' The code downloads and pre-processes data from OpenStreetMap, prepares points of interest, runs simulations, measures and saves the results and creates and plots. 


[![Growing Urban Bicycle Networks](readmevideo.gif)](https://growbike.net/city/paris)

## Folder structure
The main folder/repo is `bikenwgrowth`, containing Jupyter notebooks (`code/`), preprocessed data (`data/`), parameters (`parameters/`), result plots (`plots/`).

Other data files (network plots, videos, results, exports, logs) make up many GBs and are stored in the separate external folder `bikenwgrowth_external` due to Github's space limitations.

## Setting up code environment
### Conda yml
[Download `.yml`](env.yml)

### Manual procedure
```
conda create --override-channels -c conda-forge -n OSMNX python=3.8.2 osmnx=0.16.2 python-igraph watermark haversine rasterio tqdm geojson
conda activate OSMNX
conda install -c conda-forge ipywidgets
pip install opencv-python
conda install -c anaconda gdal
pip install --user ipykernel
python -m ipykernel install --user --name=OSMNX
```
Run Jupyter Notebook with kernel OSMNX (Kernel > Change Kernel > OSMNX)



## Running the code locally
Step-by-step execution of Jupyter notebooks:

1. Populate `parameters/cities.csv`, see below. Only data from the city of Copenhagen is needed.
2. Run 01_prepare_networks.ipynb and 02_prepare_pois.ipynb once to download and prepare all networks and POIs  
3. Run 03_preprocessing_population_density.ipynb, 04_preprocessing_bicycle_counts.ipynb,05_weighted_distances.ipynb to preprocess bicycle count and population density data and apply weighted distance measures.
4. Run 06_poi_based_generation.ipynb to grow all networks with betweenness centrality as a growth strategy
5. run 07_analyze_results.ipynb to analyze results
6. run 08_plot_results.ipynb to plot the grown networks and each growth stage

run full_code.ipynb to run all steps in one notebook.

## Populating cities.csv
### Checking nominatimstring  
* Go to e.g. [https://nominatim.openstreetmap.org/search.php?q=paris%2C+france&polygon_geojson=1&viewbox=](https://nominatim.openstreetmap.org/search.php?q=paris%2C+france&polygon_geojson=1&viewbox=) and enter the search string. If a correct polygon (or multipolygon) pops up it should be fine. If not leave the field empty and acquire a shape file, see below.

### Acquiring shape file  
* Go to [Overpass](overpass-turbo.eu), to the city, and run:
    `relation["boundary"="administrative"]["name:en"="Copenhagen Municipality"]({{bbox}});(._;>;);out skel;`
* Export: Download as GPX
* Use QGIS to create a polygon, with Vector > Join Multiple Lines, and Processing Toolbox > Polygonize (see [Stackexchange answer 1](https://gis.stackexchange.com/questions/98320/connecting-two-line-ends-in-qgis-without-resorting-to-other-software) and [Stackexchange answer 2](https://gis.stackexchange.com/questions/207463/convert-a-line-to-polygon))
