
package br.com.fatec.tcc.rotasegura.roadMap;

import java.util.HashMap;
import java.util.Map;

public class Step {

    private Distance_ distance;
    private Duration_ duration;
    private EndLocation_ end_location;
    private String html_instructions;
    private Polyline polyline;
    private StartLocation_ start_location;
    private String travel_mode;
    private String maneuver;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The distance
     */
    public Distance_ getDistance() {
        return distance;
    }

    /**
     * 
     * @param distance
     *     The distance
     */
    public void setDistance(Distance_ distance) {
        this.distance = distance;
    }

    /**
     * 
     * @return
     *     The duration
     */
    public Duration_ getDuration() {
        return duration;
    }

    /**
     * 
     * @param duration
     *     The duration
     */
    public void setDuration(Duration_ duration) {
        this.duration = duration;
    }

    /**
     * 
     * @return
     *     The endLocation
     */
    public EndLocation_ getEndLocation() {
        return end_location;
    }

    /**
     * 
     * @param end_location
     *     The end_location
     */
    public void setEndLocation(EndLocation_ end_location) {
        this.end_location = end_location;
    }

    /**
     * 
     * @return
     *     The htmlInstructions
     */
    public String getHtmlInstructions() {
        return html_instructions;
    }

    /**
     * 
     * @param html_instructions
     *     The html_instructions
     */
    public void setHtmlInstructions(String html_instructions) {
        this.html_instructions = html_instructions;
    }

    /**
     * 
     * @return
     *     The polyline
     */
    public Polyline getPolyline() {
        return polyline;
    }

    /**
     * 
     * @param polyline
     *     The polyline
     */
    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    /**
     * 
     * @return
     *     The startLocation
     */
    public StartLocation_ getStartLocation() {
        return start_location;
    }

    /**
     * 
     * @param start_location
     *     The start_location
     */
    public void setStartLocation(StartLocation_ start_location) {
        this.start_location = start_location;
    }

    /**
     * 
     * @return
     *     The travelMode
     */
    public String getTravelMode() {
        return travel_mode;
    }

    /**
     * 
     * @param travel_mode
     *     The travel_mode
     */
    public void setTravelMode(String travel_mode) {
        this.travel_mode = travel_mode;
    }

    /**
     * 
     * @return
     *     The maneuver
     */
    public String getManeuver() {
        return maneuver;
    }

    /**
     * 
     * @param maneuver
     *     The maneuver
     */
    public void setManeuver(String maneuver) {
        this.maneuver = maneuver;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
