# Data-Driven-Bicycle-Networks
Exploring a data-driven approach for urban bicycle network growth in Copenhagen.

![Data-Driven-Bicycle-Networks](readmevideo.gif)

## About The Project
This project aims to revolutionize urban bicycle networks in Copenhagen by utilizing a data-driven approach. Combining street layouts, bicycle traffic data, and population density metrics, we create an intelligent system to identify optimal locations for bicycle lane infrastructure. This computationally informed approach aims to deliver networks that are not only extensive but also efficient in terms of user accessibility and traffic flow. By leveraging methodologies from graph theory, optimization algorithms, and machine learning, this project seeks to provide urban planners with actionable insights for developing more sustainable urban transport systems.

## Table of Contents
1. [Folder Structure](#folder-structure)
2. [Setup](#setup)
    1. [Conda yml](#conda-yml)
    2. [Manual Procedure](#manual-procedure)
3. [Running the Code](#running-the-code)
4. [Populating cities.csv](#populating-citiescsv)

## Folder Structure
The main folder, `bikenwgrowth`, contains:
- Jupyter Notebooks (`code/`)
- Preprocessed Data (`data/`)
- Parameters (`parameters/`)
- Result Plots (`plots/`)

Additional data files are stored in `bikenwgrowth_external` due to GitHub's space limitations.

## Setup

### Conda yml
[Download `.yml`](env.yml)

### Manual Procedure
```bash
conda create --override-channels -c conda-forge -n OSMNX python=3.8.2 osmnx=0.16.2 python-igraph watermark haversine rasterio tqdm geojson
conda activate OSMNX
conda install -c conda-forge ipywidgets
pip install opencv-python
conda install -c anaconda gdal
pip install --user ipykernel
python -m ipykernel install --user --name=OSMNX
```

Run Jupyter Notebook with kernel OSMNX (Kernel > Change Kernel > OSMNX).

## Running the Code
Execute Jupyter notebooks in the following order:
1. `01_prepare_networks.ipynb` and `02_prepare_pois.ipynb` to download and prepare networks and POIs.
2. `03_preprocessing_population_density.ipynb`, `04_preprocessing_bicycle_counts.ipynb`, `05_weighted_distances.ipynb` to preprocess data and apply weighted distance measures.
3. `06_poi_based_generation.ipynb` to grow networks using betweenness centrality.
4. `07_analyze_results.ipynb` to analyze results.
5. `08_plot_results.ipynb` to plot the grown networks.

Alternatively, run `full_code.ipynb` to execute all steps in one go.

## Populating cities.csv
### Checking nominatimstring  
* Visit [Nominatim](https://nominatim.openstreetmap.org/search.php?q=paris%2C+france&polygon_geojson=1&viewbox=) and enter the search string. If a correct polygon appears, proceed. Otherwise, acquire a shapefile.

### Acquiring shape file  
* Visit [Overpass](https://overpass-turbo.eu) and run:
    ```
    relation["boundary"="administrative"]["name:en"="Copenhagen Municipality"]({{bbox}});(._;>;);out skel;
    ```
* Download as GPX and use QGIS to create a polygon.



---

For more details about the project, please visit [growbike.net/city/copenhagen](https://growbike.net/city/copenhagen).
