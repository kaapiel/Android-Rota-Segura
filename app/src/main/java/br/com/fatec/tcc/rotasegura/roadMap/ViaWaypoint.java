
package br.com.fatec.tcc.rotasegura.roadMap;

import java.util.HashMap;
import java.util.Map;

public class ViaWaypoint {

    private Location location;
    private int stepIndex;
    private double stepInterpolation;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * 
     * @param location
     *     The location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * 
     * @return
     *     The stepIndex
     */
    public int getStepIndex() {
        return stepIndex;
    }

    /**
     * 
     * @param stepIndex
     *     The step_index
     */
    public void setStepIndex(int stepIndex) {
        this.stepIndex = stepIndex;
    }

    /**
     * 
     * @return
     *     The stepInterpolation
     */
    public double getStepInterpolation() {
        return stepInterpolation;
    }

    /**
     * 
     * @param stepInterpolation
     *     The step_interpolation
     */
    public void setStepInterpolation(double stepInterpolation) {
        this.stepInterpolation = stepInterpolation;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
