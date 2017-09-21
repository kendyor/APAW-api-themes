package es.upm.miw.apaw.theme.api.controllers;

import java.util.ArrayList;
import java.util.List;

import es.upm.miw.apaw.theme.api.daos.DaoFactory;
import es.upm.miw.apaw.theme.api.dtos.ThemeDto;
import es.upm.miw.apaw.theme.api.entities.Theme;
import es.upm.miw.apaw.theme.api.dtos.ThemeVoteDto;

public class ThemeController {

    public List<ThemeDto> themeList() {
        List<Theme> themeList = DaoFactory.getFactory().getThemeDao().findAll();
        List<ThemeDto> themeDtoList = new ArrayList<>();
        for (Theme theme : themeList) {
            themeDtoList.add(new ThemeDto(theme));
        }
        return themeDtoList;
    }

    public void createTheme(String themeName) {
        DaoFactory.getFactory().getThemeDao().create(new Theme(themeName));
    }

    public boolean existThemeId(int themeId) {
        return DaoFactory.getFactory().getThemeDao().read(themeId) != null;
    }

    public double themeOverage(int themeId) {
        List<Integer> voteList = DaoFactory.getFactory().getVoteDao().findValueByThemeId(themeId);
        if (voteList.isEmpty()) {
            return Double.NaN;
        } else {
            double total = 0;
            for (Integer value : voteList) {
                total += value;
            }
            return total / voteList.size();
        }
    }

    public ThemeVoteDto themeVote(int themeId) {
        List<Integer> voteList = DaoFactory.getFactory().getVoteDao().findValueByThemeId(themeId);
        return new ThemeVoteDto(DaoFactory.getFactory().getThemeDao().read(themeId), voteList);
    }

}
