package eexpocrud.cfg;

public enum Glyphicon {
	asterisk, plus, euro, eur, minus, cloud, envelope, pencil, glass, music, search, heart, star, star_empty, user, //
    film, th_large, th, th_list, ok, remove, zoom_in, zoom_out, off, signal, cog, trash, home, file, time, road, //
    download_alt, download, upload, inbox, play_circle, repeat, refresh, list_alt, lock, flag, headphones, volume_off, //
    volume_down, volume_up, qrcode, barcode, tag, tags, book, bookmark, print, camera, font, bold, italic, //
    text_height, text_width, align_left, align_center, align_right, align_justify, list, indent_left, indent_right, //
    facetime_video, picture, map_marker, adjust, tint, edit, share, check, move, step_backward, fast_backward, backward, //
    play, pause, stop, forward, fast_forward, step_forward, eject, chevron_left, chevron_right, plus_sign, minus_sign, //
    remove_sign, ok_sign, question_sign, info_sign, screenshot, remove_circle, ok_circle, ban_circle, arrow_left, arrow_right, //
    arrow_up, arrow_down, share_alt, resize_full, resize_small, exclamation_sign, gift, leaf, fire, eye_open, eye_close, warning_sign, //
    plane, calendar, random, comment, magnet, chevron_up, chevron_down, retweet, shopping_cart, folder_close, folder_open, resize_vertical, //
    resize_horizontal, hdd, bullhorn, bell, certificate, thumbs_up, thumbs_down, hand_right, hand_left, hand_up, hand_down, circle_arrow_right, //
    circle_arrow_left, circle_arrow_up, circle_arrow_down, globe, wrench, tasks, filter, briefcase, fullscreen, dashboard, paperclip, //
    heart_empty, link, phone, pushpin, usd, gbp, sort, sort_by_alphabet, sort_by_alphabet_alt, sort_by_order, sort_by_order_alt, //
    sort_by_attributes, sort_by_attributes_alt, unchecked, expand, collapse_down, collapse_up, log_in, flash, log_out, new_window, //
    record, save, open, saved, import_, export, send, floppy_disk, floppy_saved, floppy_remove, floppy_save, floppy_open, credit_card, //
    transfer, cutlery, header, compressed, earphone, phone_alt, tower, stats, sd_video, hd_video, subtitles, //
    sound_stereo, sound_dolby, sound_5_1, sound_6_1, sound_7_1, copyright_mark, registration_mark, cloud_download, cloud_upload, //
    tree_conifer, tree_deciduous, cd, save_file, open_file, level_up, copy, paste, alert, equalizer, king, queen, pawn, bishop, //
    knight, baby_formula, tent, blackboard, bed, apple, erase, hourglass, lamp, duplicate, piggy_bank, scissors, bitcoin, yen, //
    ruble, scale, ice_lolly, ice_lolly_tasted, education, option_horizontal, option_vertical, menu_hamburger, modal_window, oil, grain, //
    sunglasses, text_size, text_color, text_background, object_align_top, object_align_bottom, object_align_horizontal, object_align_left, //
    object_align_vertical, object_align_right, triangle_right, triangle_left, triangle_bottom, triangle_top, console, superscript, subscript, //
    menu_left, menu_right, menu_down, menu_up;

    public String cssClass() {
        String result = toString();
        result = result.replace('_', '-');
        if (result.endsWith("-"))
            result = result.substring(0, result.length() - 1);
        return "glyphicon glyphicon-" + result;
    }
}
