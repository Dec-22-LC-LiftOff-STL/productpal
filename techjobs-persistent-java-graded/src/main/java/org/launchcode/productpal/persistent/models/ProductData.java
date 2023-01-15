package org.launchcode.productpal.persistent.models;

import java.util.ArrayList;

// This is a change made in sandbox.

/**
 * Created by LaunchCode
 */
public class ProductData {


    /**
     * Returns the results of searching the Jobs data by field and search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column Job field that should be searched.
     * @param value Value of the field to search for.
     * @param allJobs The list of jobs to search.
     * @return List of all jobs matching the criteria.
     */
    public static ArrayList<Product> findByColumnAndValue(String column, String value, Iterable<Product> allJobs) {

        ArrayList<Product> results = new ArrayList<>();

        if (value.toLowerCase().equals("all")){
            return (ArrayList<Product>) allJobs;
        }

        if (column.equals("all")){
            results = findByValue(value, allJobs);
            return results;
        }
        for (Product product : allJobs) {

            String aValue = getFieldValue(product, column);

            if (aValue != null && aValue.toLowerCase().contains(value.toLowerCase())) {
                results.add(product);
            }
        }

        return results;
    }

    public static String getFieldValue(Product product, String fieldName){
        String theValue;
        if (fieldName.equals("name")){
            theValue = product.getName();
        } else if (fieldName.equals("employer")){
            theValue = product.getCategory().toString();
        } else {
            theValue = product.getSkills().toString();
        }

        return theValue;
    }

    /**
     * Search all Job fields for the given term.
     *
     * @param value The search term to look for.
     * @param allJobs The list of jobs to search.
     * @return      List of all jobs with at least one field containing the value.
     */
    public static ArrayList<Product> findByValue(String value, Iterable<Product> allJobs) {
        String lower_val = value.toLowerCase();

        ArrayList<Product> results = new ArrayList<>();

        for (Product product : allJobs) {

            if (product.getName().toLowerCase().contains(lower_val)) {
                results.add(product);
            } else if (product.getCategory().toString().toLowerCase().contains(lower_val)) {
                results.add(product);
            } else if (product.getSkills().toString().toLowerCase().contains(lower_val)) {
                results.add(product);
            } else if (product.toString().toLowerCase().contains(lower_val)) {
                results.add(product);
            }

        }

        return results;
    }


}

