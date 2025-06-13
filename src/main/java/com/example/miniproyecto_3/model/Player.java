package com.example.miniproyecto_3.model;


import java.io.Serializable;


    public class Player implements Serializable {
        private String nickname;
        private int sunkenShips;
        private boolean ableToContinue;

        public Player(String nickname, int sunkenShips) {
            this.nickname = nickname;
            this.sunkenShips = sunkenShips;

        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getSunkenShips() {
            return sunkenShips;
        }

        public void setSunkenShips(int sunkenShips) {
            this.sunkenShips = sunkenShips;
        }

        public void incrementSunkenShips() {
            this.sunkenShips++;
        }

        @Override
        public String toString() {
            return "Player{" +
                    "nickname='" + nickname + '\'' +
                    ", sunken ships=" + sunkenShips +
                    '}';
        }

    }
