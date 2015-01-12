package com.pedrocactus.topobloc.app.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by pierrecastex on 08/01/2015.
 */
public class FeatureCollection implements Iterable<Feature>{

    private List<Feature> features = new ArrayList<Feature>();

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public FeatureCollection add(Feature feature) {
        features.add(feature);
        return this;
    }

    public void addAll(Collection<Feature> features) {
        this.features.addAll(features);
    }

    @Override
    public Iterator<Feature> iterator() {
        return features.iterator();
    }
}
