#!/usr/bin/env bash
# use GraphAware artifactory. For now, use vince's Dropbox account.

mvn clean package
rm -rf build
mkdir -p build/graphite
mkdir -p build/graphite/bin
mkdir -p build/graphite/lib

pushd build/graphite/bin
echo "GRAPHITE_HOME=" > graphite
echo 'java -jar ${GRAPHITE_HOME}/lib/graphite.jar' >> graphite
chmod 755 graphite

popd

cp target/graphite.jar build/graphite/lib

pushd build
tar -czvf graphite.tgz graphite
popd
cp build/graphite.tgz ~/Dropbox/graphaware/graphite/graphite.tgz
