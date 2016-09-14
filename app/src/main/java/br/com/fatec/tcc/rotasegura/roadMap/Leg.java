
package br.com.fatec.tcc.rotasegura.roadMap;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leg {

    private Distance distance;
    private Duration duration;
    private String end_address;
    private EndLocation end_location;
    private String start_address;
    private StartLocation start_location;
    private List<Step> steps = new ArrayList<Step>();
    private List<Object> trafficSpeedEntry = new ArrayList<Object>();
    private List<ViaWaypoint> viaWaypoint = new ArrayList<ViaWaypoint>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The distance
     */
    public Distance getDistance() {
        return distance;
    }

    /**
     * 
     * @param distance
     *     The distance
     */
    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    /**
     * 
     * @return
     *     The duration
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * 
     * @param duration
     *     The duration
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    /**
     * 
     * @return
     *     The endAddress
     */
    public String getEndAddress() {
        return end_address;
    }

    /**
     * 
     * @param end_address
     *     The end_address
     */
    public void setEndAddress(String end_address) {
        this.end_address = end_address;
    }

    /**
     * 
     * @return
     *     The endLocation
     */
    public EndLocation getEndLocation() {
        return end_location;
    }

    /**
     * 
     * @param end_location
     *     The end_location
     */
    public void setEndLocation(EndLocation end_location) {
        this.end_location = end_location;
    }

    /**
     * 
     * @return
     *     The startAddress
     */
    public String getStartAddress() {
        return start_address;
    }

    /**
     * 
     * @param start_address
     *     The start_address
     */
    public void setStartAddress(String start_address) {
        this.start_address = start_address;
    }

    /**
     * 
     * @return
     *     The startLocation
     */
    public StartLocation getStartLocation() {
        return start_location;
    }

    /**
     * 
     * @param start_location
     *     The start_location
     */
    public void setStartLocation(StartLocation start_location) {
        this.start_location = start_location;
    }

    /**
     * 
     * @return
     *     The steps
     */
    public List<Step> getSteps() {
        return steps;
    }

    /**
     * 
     * @param steps
     *     The steps
     */
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    /**
     * 
     * @return
     *     The trafficSpeedEntry
     */
    public List<Object> getTrafficSpeedEntry() {
        return trafficSpeedEntry;
    }

    /**
     * 
     * @param trafficSpeedEntry
     *     The traffic_speed_entry
     */
    public void setTrafficSpeedEntry(List<Object> trafficSpeedEntry) {
        this.trafficSpeedEntry = trafficSpeedEntry;
    }

    /**
     * 
     * @return
     *     The viaWaypoint
     */
    public List<ViaWaypoint> getViaWaypoint() {
        return viaWaypoint;
    }

    /**
     * 
     * @param viaWaypoint
     *     The via_waypoint
     */
    public void setViaWaypoint(List<ViaWaypoint> viaWaypoint) {
        this.viaWaypoint = viaWaypoint;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
