package com.happyldc.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 *
 * 命令终端工具类（需要root）
 */

public final class ShellUtils {

    private static final String LINE_SEP = System.getProperty("line.separator");

    private ShellUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 执行命令
     * @param command  输入的命令
     * @param isRoot  是否root
     * @return 命令输入结果对象
     */
    public static CommandResult execCmd(final String command, final boolean isRoot) {
        return execCmd(new String[]{command}, isRoot, true);
    }

    /**
     * 执行命令
     * @param commands 输入的命令
     * @param isRoot   是否root.
     * @return 命令输入结果对象
     */
    public static CommandResult execCmd(final List<String> commands, final boolean isRoot) {
        return execCmd(commands == null ? null : commands.toArray(new String[]{}), isRoot, true);
    }

    /**
     * 执行命令
     * @param commands 输入的命令
     * @param isRoot   是否root.
     * @return 命令输入结果对象
     */
    public static CommandResult execCmd(final String[] commands, final boolean isRoot) {
        return execCmd(commands, isRoot, true);
    }

    /**
     * 执行命令
     * @param command         输入的命令
     * @param isRoot          是否root.
     * @param isNeedResultMsg 若要返回结果的消息，则为true，否则为false。
     * @return  命令输入结果对象
     */
    public static CommandResult execCmd(final String command,
                                        final boolean isRoot,
                                        final boolean isNeedResultMsg) {
        return execCmd(new String[]{command}, isRoot, isNeedResultMsg);
    }

    /**
     * 执行命令
     * @param commands        输入的命令
     * @param isRoot         是否root.
     * @param isNeedResultMsg 若要返回结果的消息，则为true，否则为false
     * @return 命令输入结果对象
     */
    public static CommandResult execCmd(final List<String> commands,
                                        final boolean isRoot,
                                        final boolean isNeedResultMsg) {
        return execCmd(commands == null ? null : commands.toArray(new String[]{}),
                isRoot,
                isNeedResultMsg);
    }

    /**
     * 执行命令
     * @param commands        输入的命令
     * @param isRoot          是否root
     * @param isNeedResultMsg 若要返回结果的消息，则为true，否则为false
     * @return  命令输入结果对象
     */
    public static CommandResult execCmd(final String[] commands,
                                        final boolean isRoot,
                                        final boolean isNeedResultMsg) {
        int result = -1;
        if (commands == null || commands.length == 0) {
            return new CommandResult(result, null, null);
        }
        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec(isRoot ? "su" : "sh");
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) continue;
                os.write(command.getBytes());
                os.writeBytes(LINE_SEP);
                os.flush();
            }
            os.writeBytes("exit" + LINE_SEP);
            os.flush();
            result = process.waitFor();
            if (isNeedResultMsg) {
                successMsg = new StringBuilder();
                errorMsg = new StringBuilder();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream(),
                        "UTF-8"));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream(),
                        "UTF-8"));
                String line;
                if ((line = successResult.readLine()) != null) {
                    successMsg.append(line);
                    while ((line = successResult.readLine()) != null) {
                        successMsg.append(LINE_SEP).append(line);
                    }
                }
                if ((line = errorResult.readLine()) != null) {
                    errorMsg.append(line);
                    while ((line = errorResult.readLine()) != null) {
                        errorMsg.append(LINE_SEP).append(line);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                successResult.close();
                errorResult.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }
        return new CommandResult(
                result,
                successMsg == null ? null : successMsg.toString(),
                errorMsg == null ? null : errorMsg.toString()
        );
    }

    /**
     * 命令输入结果对象
     */
    public static class CommandResult {
        public int    result;
        public String successMsg;
        public String errorMsg;

        public CommandResult(final int result, final String successMsg, final String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }
    }
}
