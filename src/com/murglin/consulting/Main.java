package com.murglin.consulting;

import java.util.Scanner;

import static com.murglin.consulting.Main.ConversationState.GREETING_INFORMATION;

public class Main {

    enum ConversationState {
        GREETING_INFORMATION("Hello, John! It looks like you booked a Basic Economy flight.") {
            @Override
            public ConversationState interact(final Scanner scanner) {
                System.out.println(GREETING_INFORMATION.output);
                return LUGGAGE_NOT_AVAILABLE_QUESTION;
            }
        },
        LUGGAGE_NOT_AVAILABLE_QUESTION("Are you aware that this flight doesn't have any storage for carry-on luggage?") {
            @Override
            public ConversationState interact(final Scanner scanner) {
                System.out.println(LUGGAGE_NOT_AVAILABLE_QUESTION.output);
                final String userInput = scanner.nextLine();
                if (!userInput.equalsIgnoreCase(YES) && !userInput.equalsIgnoreCase(NO)) {
                    System.out.println("'" + YES + "' or '" + NO + "' are only valid answers.");
                    interact(scanner);
                }
                if (userInput.equalsIgnoreCase(YES)) {
                    return UPGRADE_OFFER_INFORMATION;
                }
                return SEAT_NOT_AVAILABLE_INFORMATION;
            }
        },
        SEAT_NOT_AVAILABLE_INFORMATION("Some other limitations you may want to consider is that you will not be able to pick a seat.") {
            @Override
            public ConversationState interact(final Scanner scanner) {
                System.out.println(SEAT_NOT_AVAILABLE_INFORMATION.output);
                return UPGRADE_OFFER_INFORMATION;
            }
        },
        UPGRADE_OFFER_INFORMATION("We're happy to let you know that we can upgrade you today for just $25!") {
            @Override
            public ConversationState interact(final Scanner scanner) {
                System.out.println(UPGRADE_OFFER_INFORMATION.output);
                return UPGRADE_QUESTION;
            }
        },
        UPGRADE_QUESTION("Would you like to do that now?") {
            @Override
            public ConversationState interact(final Scanner scanner) {
                System.out.println(UPGRADE_QUESTION.output);
                final String userInput = scanner.nextLine();
                if (!userInput.equalsIgnoreCase(YES_PLEASE_UPGRADE) && !userInput.equalsIgnoreCase(NOT_RIGHT_NOW)) {
                    System.out.println("'" + YES_PLEASE_UPGRADE + "' or '" + NOT_RIGHT_NOW + "' are only valid answers.");
                    interact(scanner);
                }
                if (userInput.equalsIgnoreCase(YES_PLEASE_UPGRADE)) {
                    return UPGRADED_CONCLUSION;
                }
                return NOT_UPGRADE_CONCLUSION;
            }
        },
        NOT_UPGRADE_CONCLUSION("Okay, please let our customer service team know if you change your mind.") {
            @Override
            public ConversationState interact(final Scanner scanner) {
                System.out.println(NOT_UPGRADE_CONCLUSION.output);
                System.exit(OK_EXIT_CODE);
                return this;
            }
        },
        UPGRADED_CONCLUSION("Okay, you've been upgraded!") {
            @Override
            public ConversationState interact(final Scanner scanner) {
                System.out.println(UPGRADED_CONCLUSION.output);
                System.exit(OK_EXIT_CODE);
                return this;
            }
        };

        private final String output;

        private static final String YES = "Yes";

        private static final String NO = "No";

        private static final String NOT_RIGHT_NOW = "Not right now";

        private static final String YES_PLEASE_UPGRADE = "Yes, please upgrade";

        private static final int OK_EXIT_CODE = 0;

        ConversationState(final String output) {
            this.output = output;
        }

        public abstract ConversationState interact(final Scanner scanner);
    }

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        GREETING_INFORMATION
                .interact(scanner)
                .interact(scanner)
                .interact(scanner)
                .interact(scanner)
                .interact(scanner)
                .interact(scanner);
    }
}
