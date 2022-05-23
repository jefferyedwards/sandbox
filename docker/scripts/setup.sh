#!/bin/bash

set -euxo pipefail

GEOSERVER_URL="https://sourceforge.net/projects/geoserver/files/GeoServer/${GEOSERVER_VERSION}/geoserver-${GEOSERVER_VERSION}-war.zip"
LIBJPEG_TURBO_URL="https://sourceforge.net/projects/libjpeg-turbo/files/${LIBJPEG_TURBO_VERSION}/libjpeg-turbo-official-${LIBJPEG_TURBO_VERSION}.x86_64.rpm"
PLUGIN_BASE_URL="https://sourceforge.net/projects/geoserver/files/GeoServer/${GEOSERVER_VERSION}/extensions/geoserver-${GEOSERVER_VERSION}-@TOKEN@-plugin.zip"

PLUGIN_NAMES=(
  control-flow
  csw
  gdal
  inspire
  libjpeg-turbo
  monitor
  netcdf
  printing
  pyramid
  vectortiles
  wps
)

function install_geoserver {

  curl \
    --fail \
    --location \
    --silent \
    --output /tmp/geoserver.zip \
    ${GEOSERVER_URL};

  unzip \
    -j \
    /tmp/geoserver.zip \
    geoserver.war \
   -d /tmp

  mv ${CATALINA_HOME}/webapps ${CATALINA_HOME}/webapps.dist
  mkdir ${CATALINA_HOME}/webapps

  unzip \
    /tmp/geoserver.war \
    -d ${CATALINA_HOME}/webapps/geoserver

  rm -f /tmp/geoserver.*

}
function install_geoserver_plugins {

  for PLUGIN_NAME in "${PLUGIN_NAMES[@]}"; do

    local PLUGIN_URL=$(plugin_url $PLUGIN_NAME)

    curl \
      --fail \
      --location \
      --silent \
      --output /tmp/${PLUGIN_NAME}.zip \
      ${PLUGIN_URL};

    unzip \
      -j \
      -o \
      -u \
      /tmp/${PLUGIN_NAME}.zip \
      *.jar \
     -d ${CATALINA_HOME}/webapps/geoserver/WEB-INF/lib

    rm -f /tmp/${PLUGIN_NAME}.zip

  done

}

function install_libjpeg_turbo {

  curl \
    --fail \
    --location \
    --silent \
    --output /tmp/libjpeg-turbo.rpm \
    ${LIBJPEG_TURBO_URL}

  dnf -y install \
    /tmp/libjpeg-turbo.rpm

  rm -f /tmp/libjpeg-turbo.rpm;

}

function plugin_url {
  local PLUGIN_NAME=$1
  echo ${PLUGIN_BASE_URL} | sed -r  -e 's/@TOKEN@/'${PLUGIN_NAME}'/'
}

install_geoserver

install_geoserver_plugins

install_libjpeg_turbo

mkdir -p \
  ${GEOSERVER_DATA_DIR} \
  ${GEOSERVER_LOG_DIR} \
  ${GEOWEBCACHE_CONFIG_DIR} \
  ${GEOWEBCACHE_CACHE_DIR} \
  ${NETCDF_DATA_DIR}
