package com.app.calculator

import java.lang.StrictMath.ceil


class Calculator {

    //메시지 리스트. - 중복 결과 제거를 위한 집합.
    public var messageList = arrayListOf<String>()

    //보스체력, 데미지가 주어졌을때(미달) 풀이월에 필요한 데미지 리턴.
    fun getNeedDamage(bossHp : Int, damageSum : Int) : Double{
        return ((9000*bossHp.toDouble() - 9000*damageSum.toDouble())/2099)
    }

    //보스체력, 선공 데미지, 후공 데미지가 주어진 경우
    //후공이 받는 이월 시간 계산.
    fun getTime(bosstHp :Int, firstDamage : Int, lastDamage : Int) : Int {
        var time = (firstDamage.toDouble() + lastDamage.toDouble() - bosstHp.toDouble())/lastDamage.toDouble()*90+20
        //올림 처리한다.
        return ceil(time).toInt()
    }

    //공격 - 계산하여 분기처리 한다.
    fun attack(bossHp : Int, damageArray : IntArray) {

        var message = ""
        var damageSum : Int = 0;
        //초기화
        messageList = arrayListOf<String>()

        //데미지 합계 계산.
        for(i in 0 until damageArray.size){
            damageSum += damageArray[i]
        }

        //보스가 안 잡히는 경우 -> 풀이월에 필요한 데미지를 계산해서 리턴.
        if(bossHp > damageSum){

            var needDamage = ceil(this.getNeedDamage(bossHp, damageSum)).toInt()
            message += " - 보스가 잡히지 않았습니다. 풀이월에 필요한 데미지 : "+needDamage+"만"
            messageList.add(message)

         //보스가 잡힌 경우. 조합식 리턴.
        }else {
            //조합 재귀함수 호출.
            this.permutation(damageArray, 0, damageArray.size-1, bossHp)
        }
    }

    //순열- 모든 경우의 수 조합.
    fun permutation(damageArray: IntArray, depth : Int, r : Int, bossHp : Int){

        //정지 조건
        if(depth == r){
            this.getLastTime(damageArray, bossHp)
            return
        }

        for(i in depth..r){

            var swap = damageArray[i]
            damageArray[i] = damageArray[depth]
            damageArray[depth] = swap

            //재귀호출.
            this.permutation(damageArray, depth+1, r, bossHp);

            //다시 swap
            damageArray[depth] = damageArray[i]
            damageArray[i] = swap
        }
    }

    //배열과, 보스 체력을 받아,
    //데미지 조합식을 만들고, 이를 str 메시지로 배열에 담는다.
    fun getLastTime(damageArray: IntArray, bossHp : Int){

        val lastIndex = 0; //마지막 공격자는 항상 첫번째로 가정.
        val lastDamage = damageArray[0] // 마지막 공격자의 데미지.
        var attackers = "" //마지막 공격 이전 공격자들 str
        var damageSum = 0 //막타 이전 데미지 합계
        var message = "" //결과 메시지.

        //막타 이전의 데미지 합계 계산.
        for(i in 1 until damageArray.size){

            //다음 데미지를 받으면 보스가 죽는 경우. 리턴.
            if(damageSum + damageArray[i] >= bossHp){
                break;
            }

            damageSum += damageArray[i]
            attackers += damageArray[i]
            attackers += ", "
        }

        //못잡는 경우. 리턴.
        if(damageSum + lastDamage < bossHp){
            return
        }

        var time = this.getTime(bossHp, damageSum, lastDamage)

        //계산이 잘못된 경우 -> 패스.
        if(time < 0){
            return;
        }

        //마지막 쉼표 제거.
        if(!"".equals(attackers)){
            attackers = attackers.substring(0, attackers.length - 2)
            message = " -공격자 ["+attackers+"]"+"\n"+"막타 : "+lastDamage+" 인 경우 이월 시간 : "+time+"초"
        }else {
            message = " -공격자 "+lastDamage+", 이월 시간 : "+time+"초"
        }

        //중복 메시지 제거.
        for(i in 0 until messageList.size){

            if(message.equals(messageList[i])){
                return
            }
        }
        //중복이 없는 경우 메시지 저장.
        messageList.add(message)
    }


}

