package group11.comp3211.view;

enum JCString {
    WELCOME_BANNER("""
              888888    888     888    888b    888     .d8888b.     888         8888888888
                "88b    888     888    8888b   888    d88P  Y88b    888         888
                 888    888     888    88888b  888    888    888    888         888
                 888    888     888    888Y88b 888    888           888         8888888
                 888    888     888    888 Y88b888    888  88888    888         888
                 888    888     888    888  Y88888    888    888    888         888
                 88P    Y88b. .d88P    888   Y8888    Y88b  d88P    888         888
                 888     "Y88888P"     888    Y888     "Y8888P88    88888888    8888888888
               .d88P
             .d88P"
            888P"
            """), START_MENU("""
                                  $MM    MM  EEEEEEE  NN   NN  UU   UU%
                                  $MMM  MMM  EE       NNN  NN  UU   UU%
                                  $MM MM MM  EEEEE    NN N NN  UU   UU%
                                  $MM    MM  EE       NN  NNN  UU   UU%
                                  $MM    MM  EEEEEEE  NN   NN   UUUUU %
            ===============================================================================
            #                                                                             #
            #                                                                             #
            #                                                                             #
            #                                                                             #
            #                                                                             #
            #                                                                             #
            ===============================================================================
            """);
    final String string;

    JCString(String string) {
        this.string = string;
    }
}
