package com.narration;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.polly.model.DescribeVoicesRequest;
import software.amazon.awssdk.services.polly.model.Voice;
import software.amazon.awssdk.services.polly.model.DescribeVoicesResponse;
import software.amazon.awssdk.services.polly.model.PollyException;

import java.util.Iterator;
import java.util.stream.Stream;

public class VoiceList {
    // Private constructor to prevent instantiation
    private VoiceList(){}

    /**
     * Shows the available voices in the specified AWS region using the Polly service.
     *
     * @param region The AWS region to retrieve voices from.
     */
    public static void showVoices(Region region){
        PollyClient polly = PollyClient.builder().region(region).build();

        displayVoices(polly); // Calls method to display voices
        polly.close(); // Closes the Polly client connection
    }

    /**
     * Retrieves and displays the voices from the Polly service.
     *
     * @param polly The PollyClient instance to use for retrieving voices.
     */
    private static void displayVoices(PollyClient polly) {
        try {
            DescribeVoicesRequest describeVoiceRequest = DescribeVoicesRequest.builder()
                    .engine("standard") // Specifies the engine type to retrieve voices
                    .build();

            DescribeVoicesResponse describeVoicesResult = polly.describeVoices(describeVoiceRequest);
            Stream<Voice> voiceStream = describeVoicesResult.voices().stream();

            Iterator<Voice> voices = voiceStream.iterator();

            while(voices.hasNext()){
                Voice voice = voices.next();
                
                // Prints the name, gender, and language of each voice
                System.out.println(voice.name() + ": " + voice.genderAsString() + " " + voice.languageName());
                
            }

        } catch (PollyException e) {
            System.err.println(e.getMessage()); // Prints error message if an exception occurs
            System.exit(1); // Exits the program with an error status
        }
    }

    /**
     * Main method to execute the application and display available voices.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args){
        VoiceList.showVoices(Region.EU_WEST_3); // Calls showVoices for a specific region
    }
}