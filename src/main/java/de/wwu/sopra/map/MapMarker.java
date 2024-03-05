package de.wwu.sopra.map;

import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.Marker;
import com.sothawo.mapjfx.event.MarkerEvent;
import javafx.animation.Transition;
import javafx.util.Duration;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Wrapper für einen MapMarker mit zusätzlichen Informationen
 *
 * @param <T> Verknüpfter Datentyp
 */
class MapMarker <T> {
    /**
     * Der MapView, auf welchem der Marker angezeigt wird.
     */
    private final MapView parent;
    /**
     * Der Marker, welcher auf dem MapView angezeigt wird.
     */
    private Marker marker;
    /**
     * Das mit dem Marker assoziierte Objekt
     */
    final T Object;
    /**
     * Diese Aktion wird beim Klick auf den Marker ausgeführt.
     */
    private Consumer<T> onClickAction;
    /**
     * Dies ist die Standardfarbe des Markers.
     */
    private final Marker.Provided baseColor;
    /**
     * Diese Farbe nimmt der Marker beim Klick auf ihn an.
     */
    private Marker.Provided clickColor;
    /**
     * Ist der Marker ausgewählt
     */
    private boolean selected;
    /**
     * Ist die Farbe des Markers verändert
     */
    private boolean colorChanged;
    /**
     * Wird der Marker aktuell auf dem MapView angezeigt
     */
    private boolean isShown;

    /**
     * Instanziiert einen neuen Marker
     *
     * @param parent           Der MapView auf welchem der Marker angezeigt werden soll
     * @param object           Das mit dem Marker assoziierte Objekt
     * @param locationSelector Eine Funktion, die die Position des Objektes spezifiziert
     * @param color            Die Farbe des Markers
     */
    MapMarker(
            MapView parent,
            T object,
            Function<T, Coordinate> locationSelector,
            Marker.Provided color) {
        this.parent = parent;
        this.Object = object;
        this.baseColor = color;
        this.marker = Marker
                .createProvided(this.baseColor)
                .setPosition(locationSelector.apply(object))
                .setVisible(true);
    }

    /**
     * Instanziiert einen neuen Marker
     *
     * @param parent   Der MapView auf welchem der Marker angezeigt werden soll
     * @param location Position des Markers auf der Map
     * @param color    Die Farbe des Markers
     */
    MapMarker(
            MapView parent,
            Coordinate location,
            Marker.Provided color) {
        this.parent = parent;
        this.Object = null;
        this.baseColor = color;
        this.marker = Marker
                .createProvided(this.baseColor)
                .setPosition(location)
                .setVisible(true);
    }

    /**
     * Ruft den aktuell angezeigten Marker ab.
     *
     * @return Marker dieser Instanz
     */
    Marker getMarker() {
        return this.marker;
    }

    /**
     * Setzt eine Aktion, welche beim Klick auf den Marker ausgeführt werden soll.
     *
     * @param action      Die Aktion
     * @param changeColor Eine Farbe, die der Marker beim Klick annehmen soll (kann null sein)
     */
    void setOnAction(Consumer<T> action, Marker.Provided changeColor) {
        this.onClickAction = action;
        this.clickColor = changeColor;
    }

    /**
     * Entfernt die in setOnAction spezifizierte Aktion.
     */
    void removeOnAction() {
        this.onClickAction = null;
        this.clickColor = null;
    }

    /**
     * Führt die durch setOnAction spezifizierte Funktion aus
     * und ruft select() bzw. deselect() auf den Marker auf.
     *
     * @param event Klick-Event des MapView (kann null sein)
     */
    void click(MarkerEvent event) {
        if (this.onClickAction == null)
            return;

        if (event != null && event.getMarker() != this.marker) {
            deselect();
            return;
        }

        if (this.selected)
            deselect();
        else
            select();

        onClickAction.accept(Object);
    }

    /**
     * Wählt den Marker aus.
     * Falls setOnAction gesetzt wurde, nimmt er die dort spezifizierte Farbe an.
     */
    void select() {
        select(null);
    }

    /**
     * Wählt den Marker aus.
     * Falls color nicht null ist, nimmt er die angegebene Farbe an.
     * Ansonsten nimmt er, falls setOnAction gesetzt wurde, die dort spezifizierte Farbe an.
     *
     * @param color Anzunehmende Farbe (kann null sein)
     */
    void select(Marker.Provided color) {
        this.selected = true;
        if (color != null)
            changeColor(color);
        else if (this.clickColor != null)
            changeColor(this.clickColor);
    }

    /**
     * Wählt den Marker ab.
     * Falls setOnAction gesetzt wurde,
     */
    void deselect() {
        this.selected = false;
        if (this.colorChanged) {
            changeColor(this.baseColor);
        }
    }

    /**
     * Zeigt den Marker auf der Map an.
     */
    void add() {
        if (!this.isShown && this.parent.getInitialized()) {
            this.parent.addMarker(this.marker);
            this.isShown = true;
        }
    }

    /**
     * Entfernt den Marker von der Map.
     */
    void remove() {
        if (this.isShown) {
            this.parent.removeMarker(this.marker);
            this.isShown = false;
        }
    }

    /**
     * Wechselt die Farbe des Markers.
     *
     * @param changeColor Anzunehmende Farbe
     */
    void changeColor(Marker.Provided changeColor) {
        this.colorChanged = this.baseColor != changeColor;

        var newMarker = Marker
                .createProvided(changeColor)
                .setPosition(marker.getPosition())
                .setVisible(true);

        if (isShown) {
            this.parent.removeMarker(this.marker);
            this.parent.addMarker(newMarker);
        }

        this.marker = newMarker;
    }

    /**
     * Bewegt den Marker an eine neue Position.
     *
     * @param position Neue Position
     * @param animated Soll die Bewegung animiert werden
     */
    void move(Coordinate position, boolean animated) {
        if (this.isShown && animated) {
            animateMovement(position);
        }
        else
            this.marker.setPosition(position);
    }

    /**
     * Animiert die Bewegung eines Markers an eine neue Position.
     *
     * @param newPosition Neue Position des Markers
     */
    private void animateMovement(Coordinate newPosition) {
        var oldPosition = this.marker.getPosition();

        final Transition transition = new Transition() {
            private final Double oldPositionLongitude = oldPosition.getLongitude();
            private final Double oldPositionLatitude = oldPosition.getLatitude();
            private final double deltaLatitude = newPosition.getLatitude() - oldPositionLatitude;
            private final double deltaLongitude = newPosition.getLongitude() - oldPositionLongitude;

            {
                setCycleDuration(Duration.seconds(1.0));
                setOnFinished(evt -> marker.setPosition(newPosition));
            }

            @Override
            protected void interpolate(double v) {
                final double latitude = oldPosition.getLatitude() + v * deltaLatitude;
                final double longitude = oldPosition.getLongitude() + v * deltaLongitude;
                marker.setPosition(new Coordinate(latitude, longitude));
            }
        };

        transition.play();
    }
}
