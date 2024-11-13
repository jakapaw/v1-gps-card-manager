package dev.jakapaw.giftcard.seriesmanager.rest.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IssueDetailDTO {

    public String issuer;
    public int cardIssued;
    public double totalValue;
    public ExpiryDuration expiryDuration;

    public static class ExpiryDuration {
        public int d;
        public int m;
        public int y;

        public ExpiryDuration(int d, int m, int y) {
            this.d = d;
            this.m = m;
            this.y = y;
        }
    }
}

