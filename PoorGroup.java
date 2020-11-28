package com.doan.design.pattern;

import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;

/**
 * A thread-safe container that stores a group ID and members.
 * <p>
 * It can be added <tt>Member</tt> and return a member list as String.
 * Also, it can start and stop a background task that writes a member list to specified files.
 * <p>
 * This class is called a lot, so we need improve it.
 */
@ThreadSafe
public class PoorGroup {
    private String groupId;
    private HashSet<Member> members;
    private boolean shouldStop;

    public PoorGroup(String groupId) {
        this.groupId = groupId;
        this.members = new HashSet<Member>();
    }

    public synchronized void addMember(Member member) {
        if (member == null) {
            return;
        }
        members.add(member);
    }

    public String getMembersAsStringWith10xAge() {
        StringBuilder buf = new StringBuilder();
        for (Member member : members) {
            int age = member.getAge();
            // Don't ask the reason why `age` should be multiplied ;)
            age *= 10;
            buf.append(String.format("memberId=%s, age=%d¥n", member.getMemberId(), age));
        }
        return buf.toString();
    }

    /**
     * Run a background task that writes a member list to specified files 10 times in background thread
     * so that it doesn't block the caller's thread.
     */
    public void startLoggingMemberList10Times(final String outputFilePrimary, final String outputFileSecondary) {
        new Thread(new Runnable() {
            private FileWriter writerfilePrimary;
            private FileWriter writerfileSecondary;
            @Override
            public void run() {
                int i = 1;
                try {
                    while (!shouldStop) {
                        if (i++ > 10)
                            break;
                        if (writerfilePrimary == null) {
                            writerfilePrimary = new FileWriter(new File(outputFilePrimary));
                        }
                        if (writerfilePrimary == null) {
                            writerfileSecondary = new FileWriter(new File(outputFileSecondary));
                        }
                        writerfilePrimary.append(PoorGroup.this.getMembersAsStringWith10xAge());
                        writerfileSecondary.append(PoorGroup.this.getMembersAsStringWith10xAge());
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(String.format("Unexpected error occurred. Please check these file names. outputFilePrimary=%s, outputFileSecondary=%s", outputFilePrimary, outputFileSecondary));
                } finally {
                    try {
                        if (writerfilePrimary != null)
                            writerfilePrimary.close();
                        if (writerfileSecondary != null)
                            writerfileSecondary.close();
                    } catch (Exception e) {
                        // Do nothing since there isn't anything we can do here, right?
                    }
                }
            }
        }).start();
    }

    /**
     * Stop the background task started by <tt>startPrintingMemberList()</tt>
     * Of course, <tt>startLoggingMemberList</tt> can be called again after calling this method.
     */
    public synchronized void stopPrintingMemberList() {
        if (shouldStop == false) {
            shouldStop = true;
        }
    }
}


class Member {
    private String memberId;
    private int age;

    Member(String memberId, int age) {
        this.memberId = memberId;
        this.age = age;
    }

    public String getMemberId() {
        return memberId;
    }

    public int getAge() {
        return age;
    }

    public boolean equals(Object o) {
        // If `memberId` matches the other's one, they should be treated as the same `Member` objects.
        if (o != null && o instanceof Member) {
            Member member = (Member) o;
            return this.getMemberId().equals(member.getMemberId());
        }
        return false;
    }
}
