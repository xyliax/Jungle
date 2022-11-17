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
            #                        Option 1   ===>   NEW     GAME                       @
            #                        Option 2   ===>   LOAD    GAME                       @
            #                        Option 3   ===>   SHOW    HELP                       @
            #                        Option 4   ===>   QUIT    GAME                       @
            |                    Press 1/2/3/4   => Select Option 1/2/3/4                 @
            |                Press 'SPACE'/'TAB' => Move to the Next Option               @
            |                Press  'SHIFT+TAB'  => Move to the Previous Option           @
            ===============================================================================
            """), KEY_MAPPING_CT("""
            -----------------------------------
            |[棋子符號][控製鍵] [功能]  [方向鍵]
            |   象        8  | 向上移動   w/↑
            |   獅        7  | 向左移動   a/←
            |   虎        6  | 向下移動   s/↓
            |   豹        5  | 向右移動   d/→
            |   狼        4  | 暫停遊戲    :
            |   狗        3  | 切換語言   TAB
            |   貓        2  | 返回主菜單 ESC
            |   鼠        1  |
            -----------------------------------
            """), KEY_MAPPING_EN("""
            -----------------------------------
            |[SYM][KEY]       [FUNC]     [KEY]
            | EP   8 Elephant| UP         w/↑
            | LN   7 Lion    | LEFT       a/←
            | TG   6 Tiger   | DOWN       s/↓
            | LP   5 Leopard | RIGHT      d/→
            | WF   4 Wolf    | Pause Game  :
            | DG   3 Dog     | Language   TAB
            | CT   2 Cat     | BACK       ESC
            | MS   1 Mouse   |
            |----------------------------------
            """);
    final String string;

    JCString(String string) {
        this.string = string;
    }
}
