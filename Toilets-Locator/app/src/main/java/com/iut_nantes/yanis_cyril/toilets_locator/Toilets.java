package com.iut_nantes.yanis_cyril.toilets_locator;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Cyril on 31/03/2016.
 */
public class Toilets {

    private int _id;
    private String _adresse;
    private String _commune;
    private String _pole;
    private String _type;
    private String _auto;
    private String _accessibilite;
    private String _horaire;
    private LatLng _location;
    private String _place;
    private double _distance;

    public Toilets(int id, String adresse, String commune, String pole, String type, String auto, String accessibilite, String horaire, LatLng location, String place) {
        _id = id;
        _adresse = adresse;
        _commune = commune;
        _pole = pole;
        _type = type;
        _auto = auto;
        _accessibilite = accessibilite;
        _horaire = horaire;
        _location = location;
        _place = place;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_adresse() {
        return _adresse;
    }

    public void set_adresse(String _adresse) {
        this._adresse = _adresse;
    }

    public String get_commune() {
        return _commune;
    }

    public void set_commune(String _commune) {
        this._commune = _commune;
    }

    public String get_pole() {
        return _pole;
    }

    public void set_pole(String _pole) {
        this._pole = _pole;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_auto() {
        return _auto;
    }

    public void set_auto(String _auto) {
        this._auto = _auto;
    }

    public String get_accessibilite() {
        return _accessibilite;
    }

    public void set_accessibilite(String _accessibilite) {
        this._accessibilite = _accessibilite;
    }

    public String get_horaire() {
        return _horaire;
    }

    public void set_horaire(String _horaire) {
        this._horaire = _horaire;
    }

    public LatLng get_location() {
        return _location;
    }

    public void set_location(LatLng _location) {
        this._location = _location;
    }

    public double get_distance() {
        return _distance;
    }

    public void set_distance(double _distance) {
        this._distance = _distance;
    }

    public String get_place() {
        return _place;
    }

    public void set_place(String _place) {
        this._place = _place;
    }

    @Override
    public String toString() {
        return "Toilets{" +
                "_id=" + _id +
                ", _distance='" + _distance + '\'' +
                '}';
    }
}
