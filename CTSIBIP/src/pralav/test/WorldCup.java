package pralav.test;

public class WorldCup {

    public static void main(String[] args) {

        Player pralav = new Player();

        pralav.addQuarters(new String[] { "BRA", "COL", "URU", "GER" });
        pralav.addSemis(new String[] { "BRA", "COL" });
        pralav.addFinals(new String[] { "BRA", "COL" });

        Sixteen six = new Sixteen();
        six.matches[0] = new Match("BRA", "CHI");
        six.matches[1] = new Match("COL", "URU");
        six.matches[2] = new Match("FRA", "NGA");
        six.matches[3] = new Match("GER", "ALG");
        six.matches[4] = new Match("NED", "MEX");
        six.matches[5] = new Match("CRC", "GER");
        six.matches[6] = new Match("ARG", "SUI");
        six.matches[7] = new Match("BEL", "USA");

        Quarters quarters = new Quarters();
        quarters.matches[0] = new Match(six.matches[0].getWinner(), six.matches[1].getWinner());
        quarters.matches[1] = new Match(six.matches[2].getWinner(), six.matches[3].getWinner());
        quarters.matches[2] = new Match(six.matches[4].getWinner(), six.matches[5].getWinner());
        quarters.matches[3] = new Match(six.matches[6].getWinner(), six.matches[7].getWinner());

        Semis semis = new Semis();
        semis.matches[0] = new Match(quarters.matches[0].getWinner(), quarters.matches[1].getWinner());
        semis.matches[1] = new Match(quarters.matches[2].getWinner(), quarters.matches[3].getWinner());

        Finals f = new Finals();
        f.match = new Match(semis.matches[0].getWinner(), semis.matches[1].getWinner());

        System.out.println(f.match.getWinner());

        System.out.println(pralav.calculateScore(quarters, semis, f));
    }

    static class Player {
        String[] quartersTeams = new String[8];
        String[] semisTeams = new String[4];
        String[] finalsTeams = new String[2];
        String winner;

        public void addQuarters(String[] teams) {
            for (int i = 0; i < teams.length; i++) {
                this.quartersTeams[i] = teams[i];
            }
        }

        public void addSemis(String[] teams) {
            for (int i = 0; i < teams.length; i++) {
                this.semisTeams[i] = teams[i];
            }
        }

        public void addFinals(String[] teams) {
            for (int i = 0; i < teams.length; i++) {
                this.finalsTeams[i] = teams[i];
            }
        }

        public int calculateScore(Quarters q, Semis s, Finals f) {
            int score = 0;
            for (String qTeam : this.quartersTeams) {
                if (q.getTeams().contains(qTeam)) {
                    score += 4;
                }
            }

            for (String sTeam : this.semisTeams) {
                if (s.getTeams().contains(sTeam)) {
                    score += 6;
                }
            }

            for (String sTeam : this.finalsTeams) {
                if (f.getTeams().contains(sTeam)) {
                    score += 8;
                }
            }

            return score;
        }
    }

    static class Match {
        String teamA;
        String teamB;
        String winner;

        public Match(String teamA, String teamB) {
            this.teamA = teamA;
            this.teamB = teamB;
        }

        public String getWinner() {
            String winner = Math.random() > 0.5 ? this.teamA : this.teamB;
            System.out.println(winner);
            return winner;
        }
    }

    static abstract class Rounds {
        java.util.Set<String> getTeams() {
            java.util.Set<String> teams = new java.util.HashSet<>();
            for (Match match : this.getMatches()) {
                teams.add(match.teamA);
                teams.add(match.teamB);
            }
            return teams;
        }

        abstract Match[] getMatches();
    }

    static class Quarters extends Rounds {
        Match[] matches = new Match[4];

        @Override
        Match[] getMatches() {
            return this.matches;
        }

    }

    static class Semis extends Rounds {
        Match[] matches = new Match[2];

        @Override
        Match[] getMatches() {
            return this.matches;
        }
    }

    static class Sixteen extends Rounds {
        Match[] matches = new Match[8];

        @Override
        Match[] getMatches() {
            return this.matches;
        }
    }

    static class Finals extends Rounds {

        Match match;
        Match[] matches = new Match[] { this.match };

        @Override
        Match[] getMatches() {
            return new Match[] { this.match };
        }
    }
}
