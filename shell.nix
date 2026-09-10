{ pkgs ? import <nixpkgs> {} }:

(pkgs.buildFHSEnv {
  name = "retrotweaker-dev-env";
  targetPkgs = pkgs: (with pkgs; [
    jdk8
    git
    unzip
    wget
  ]);
  runScript = "bash";
}).env
