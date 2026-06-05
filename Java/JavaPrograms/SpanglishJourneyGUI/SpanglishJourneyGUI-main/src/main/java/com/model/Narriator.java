package com.model;

import javazoom.jl.decoder.JavaLayerException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.polly.model.DescribeVoicesRequest;
import software.amazon.awssdk.services.polly.model.Voice;
import software.amazon.awssdk.services.polly.model.DescribeVoicesResponse;
import software.amazon.awssdk.services.polly.model.OutputFormat;
import software.amazon.awssdk.services.polly.model.PollyException;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechRequest;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechResponse;

import java.io.IOException;
import java.io.InputStream;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackListener;

/**
 * The Narriator class provides functionality for converting text to speech
 * using AWS Polly
 * and playing the generated speech using an audio player.
 * 
 * This class interacts with AWS Polly to request a voice, synthesize speech
 * from text,
 * and then plays the speech as audio using the JavaZoom MP3 player library.
 * 
 * 
 * 
 * Author: Keaton Hill
 */
public class Narriator {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Narriator() {
    }

    /**
     * Converts the provided text into speech using AWS Polly and plays the audio.
     * 
     * @param text The text to be synthesized and played as speech.
     */
    public static void playSound(String text) {
        PollyClient polly = PollyClient.builder().region(Region.US_EAST_1).build();

        talkPolly(polly, text);
        polly.close();
    }

    /**
     * Uses AWS Polly to convert the provided text into speech and plays the audio.
     * The voice used is Joanna, an English voice.
     * 
     * @param polly The PollyClient instance used to communicate with AWS Polly.
     * @param text  The text to be converted into speech.
     */
    private static void talkPolly(PollyClient polly, String text) {
        try {
            // Request available voices in English
            DescribeVoicesRequest describeVoiceRequest = DescribeVoicesRequest.builder()
                    .languageCode("en-US")
                    .build();

            DescribeVoicesResponse describeVoicesResult = polly.describeVoices(describeVoiceRequest);
            Voice voice = describeVoicesResult.voices().stream()
                    .filter(v -> v.name().equals("Joanna"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Voice not found"));

            // Synthesize speech and play the sound
            InputStream stream = synthesize(polly, text, voice, OutputFormat.MP3);
            AdvancedPlayer player = new AdvancedPlayer(stream,
                    javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());

            player.setPlayBackListener(new PlaybackListener() {
            });

            player.play();

        } catch (PollyException | JavaLayerException | IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Synthesizes speech from text using AWS Polly and returns the generated audio
     * stream.
     * 
     * @param polly  The PollyClient instance used to communicate with AWS Polly.
     * @param text   The text to be converted into speech.
     * @param voice  The AWS Polly voice used for synthesis.
     * @param format The output format of the synthesized speech (e.g., MP3).
     * @return The InputStream containing the synthesized speech audio data.
     * @throws IOException if an error occurs during speech synthesis.
     */
    public static InputStream synthesize(PollyClient polly, String text, Voice voice, OutputFormat format)
            throws IOException {
        SynthesizeSpeechRequest synthReq = SynthesizeSpeechRequest.builder()
                .text(text)
                .voiceId(voice.id())
                .outputFormat(format)
                .build();

        ResponseInputStream<SynthesizeSpeechResponse> synthRes = polly.synthesizeSpeech(synthReq);
        return synthRes;
    }
}
